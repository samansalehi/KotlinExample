package com.signicat.interview

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
class InterviewApplication

fun main(args: Array<String>) {
	runApplication<InterviewApplication>(*args)
}
