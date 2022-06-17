package com.signicat.interview.persistance

import com.signicat.interview.domain.Subject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository :JpaRepository<Subject,Long> {
    fun findByUsername(userName:String) :Subject
}