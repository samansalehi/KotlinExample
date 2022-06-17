package com.signicat.interview.contoller

import com.signicat.interview.domain.Subject
import com.signicat.interview.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityNotFoundException
import javax.validation.Valid

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearerAuth")
internal class UserController(val userService: UserService) {
    val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping
    internal fun findAll(): ResponseEntity<MutableList<Subject>> {
        return userService.findAll()
    }

    @Operation(summary = "Create new User", description = "create new Subject with it's groups")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Successful Operation"),
        ]
    )
    @PostMapping("signup")
    internal fun createUser(@RequestBody user: @Valid Subject): ResponseEntity<Subject> {
        val subject = userService.signUp(user)
        logger.info("New user is created: " + subject.id)
        return ResponseEntity<Subject>(subject, HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long) {
        userService.deleteById(id)
        logger.info("User with Id: $id is deleted")
    }

    @PutMapping("/{id}")
    internal fun updateUser(@RequestBody user: Subject,@PathVariable id: Long) {
        if (userService.findById(id).isPresent) {
            user.id=id
            userService.update(user)
            logger.info("user with id  $user.id  is updated!")
        } else {
            throw EntityNotFoundException("User with id " + user.id + "not found");
        }

    }
}