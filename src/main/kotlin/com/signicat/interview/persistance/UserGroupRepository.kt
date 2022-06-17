package com.signicat.interview.persistance

import com.signicat.interview.domain.Subject
import com.signicat.interview.domain.UserGroup
import com.signicat.interview.domain.dto.UserGroupDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserGroupRepository :JpaRepository<UserGroup,Long> {
}