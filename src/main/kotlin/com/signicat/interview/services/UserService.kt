package com.signicat.interview.services

import com.signicat.interview.domain.Subject
import com.signicat.interview.persistance.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository, val bCryptPasswordEncoder: BCryptPasswordEncoder) {

    fun signUp(subject: Subject): Subject {
        subject.password = bCryptPasswordEncoder.encode(subject.password)
        return userRepository.save(subject)
    }

    fun findAll() = ResponseEntity(userRepository.findAll(), HttpStatus.OK)

    fun findById(id: Long) = userRepository.findById(id)

    fun deleteById(id: Long) = userRepository.deleteById(id)

    fun update(user: Subject) {
        user.password = bCryptPasswordEncoder.encode(user.password)
        userRepository.save(user)
    }

}