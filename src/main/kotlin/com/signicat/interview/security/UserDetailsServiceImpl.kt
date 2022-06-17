package com.signicat.interview.security

import com.signicat.interview.domain.Subject
import com.signicat.interview.persistance.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(val userRepository: UserRepository) : UserDetailsService {
    @Transactional(readOnly = true)
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user =userRepository.findByUsername(username) ?:throw UsernameNotFoundException(username)
        return User(user.username, user.password, emptyList())
    }

    fun save(user: Subject) {
        userRepository.save(user)
    }
}