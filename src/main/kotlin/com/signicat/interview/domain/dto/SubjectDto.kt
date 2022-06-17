package com.signicat.interview.domain.dto

import com.signicat.interview.domain.Subject

class SubjectDto(
    val id:Long,
    val username: String,
    val groups: Set<UserGroupDto>
)

