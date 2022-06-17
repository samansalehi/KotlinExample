package com.signicat.interview.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.signicat.interview.domain.Subject
import com.signicat.interview.domain.UserGroup
import com.signicat.interview.domain.dto.SubjectDto
import com.signicat.interview.domain.dto.UserGroupDto
import com.signicat.interview.persistance.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(authManager: AuthenticationManager,
                              val tokenFactory: TokenFactory,val userRepository: UserRepository) :
    UsernamePasswordAuthenticationFilter() {

    init {
        authenticationManager = authManager
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val credential = ObjectMapper().readValue(request?.inputStream, Subject::class.java)
        return authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                credential.username,
                credential.password,
                emptyList<GrantedAuthority>()
            )
        )
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val credential = userRepository.findByUsername(authResult!!.name)

        val jwt = tokenFactory.generate(credential.mapToDto())
        response?.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt)
    }
}