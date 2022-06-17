package com.signicat.interview.exception


import org.apache.kafka.common.errors.AuthorizationException
import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.PrintWriter
import java.io.StringWriter
import java.text.ParseException
import javax.persistence.EntityNotFoundException
import javax.persistence.NoResultException


@ControllerAdvice
class ControllerExceptionHandler: ResponseEntityExceptionHandler()  {

    @ExceptionHandler(value = [
        ConstraintViolationException::class,
        HttpClientErrorException.BadRequest::class,
        IllegalArgumentException::class]
    )
    fun constraintViolationException(e:Exception) : ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, "Bad request", e)
    }

    @ExceptionHandler(value = [AuthorizationException::class, ParseException::class])
    fun unauthorizedException(e: Exception) : ResponseEntity<ErrorResponse>
    {
        return generateErrorResponse(HttpStatus.FORBIDDEN, "You are not authorized to do this operation", e)
    }

    @ExceptionHandler(value = [AuthenticationException::class])
    fun forbiddenException(e: Exception):ResponseEntity<ErrorResponse>
    {
        return generateErrorResponse(HttpStatus.UNAUTHORIZED, "You are not allowed to do this operation", e)
    }

    @ExceptionHandler(value = [
        EntityNotFoundException::class,
        NoSuchElementException::class,
        NoResultException::class,
        EmptyResultDataAccessException::class,
        IndexOutOfBoundsException::class,
        KotlinNullPointerException::class]
    )
    fun notFoundException(e: Exception): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.NOT_FOUND, "Resource not found", e)
    }

    @ExceptionHandler(
        value = [Exception::class]
    )
    fun internalServerErrorException(e: Exception): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Generic internal error", e)
    }

    private fun generateErrorResponse(status: HttpStatus, message: String, e: Exception): ResponseEntity<ErrorResponse> {
      val sw= StringWriter()
        val pw= PrintWriter(sw)
        e.printStackTrace(pw)
        val stackTrace = sw.toString()
        val stackTraceMessage =
            when (System.getenv("ENV")) {
                "STAGING" -> stackTrace // returning the stack trace
                "PRODUCTION" -> null // returning no stack trace
                else -> null // default behavior
            }

        return ResponseEntity(ErrorResponse(status, message, stackTraceMessage), status)
    }

}