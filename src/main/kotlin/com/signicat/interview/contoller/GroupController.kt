package com.signicat.interview.contoller

import com.signicat.interview.domain.Subject
import com.signicat.interview.domain.dto.UserGroupDto
import com.signicat.interview.persistance.UserRepository
import com.signicat.interview.services.UserGroupService
import com.signicat.interview.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityNotFoundException
import javax.validation.Valid

@RestController
@RequestMapping("/group")
internal class GroupController(val userGroupService: UserGroupService) {
    val logger: Logger = LoggerFactory.getLogger(GroupController::class.java)

    @GetMapping
    internal fun findAll(): ResponseEntity<List<UserGroupDto>> {
        return userGroupService.findAll()
    }

    @PostMapping("signup")
    internal fun createUser(@RequestBody group: @Valid UserGroupDto): ResponseEntity<UserGroupDto> {
        val group = userGroupService.signUp(group)
        logger.info("New group is created: " + group.id)
        return ResponseEntity<UserGroupDto>(group, HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long) {
        userGroupService.deleteById(id)
        logger.info("Group with Id: $id is deleted")
    }

    @PutMapping("/{id}")
    internal fun updateUser(@RequestBody group: UserGroupDto,@PathVariable id: Long) {
        if (userGroupService.findById(id).isPresent) {
            group.id=id
            userGroupService.update(group)
            logger.info("group with id  $group.id  is updated!")
        } else {
            throw EntityNotFoundException("group with id " + group.id + "not found");
        }

    }
}