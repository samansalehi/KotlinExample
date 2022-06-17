package com.signicat.interview.services

import com.signicat.interview.domain.Subject
import com.signicat.interview.domain.UserGroup
import com.signicat.interview.domain.dto.UserGroupDto
import com.signicat.interview.persistance.UserGroupRepository
import com.signicat.interview.persistance.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserGroupService(val userGroupRepository: UserGroupRepository) {

    fun signUp(userGroupDto: UserGroupDto): UserGroupDto {

         return userGroupRepository.save(UserGroup(userGroupDto.id,userGroupDto.name)).mapToDto()
    }

    fun findAll() =
        ResponseEntity(userGroupRepository.findAll().map { e -> e.mapToDto() }, HttpStatus.OK)


    fun findById(id: Long) = userGroupRepository.findById(id)

    fun deleteById(id: Long) = userGroupRepository.deleteById(id)

    fun update(userGroupDto: UserGroupDto) {
        userGroupRepository.save(UserGroup(userGroupDto.id,userGroupDto.name))
    }
}

