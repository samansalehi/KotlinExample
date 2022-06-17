package com.signicat.interview.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.util.*

class ErrorResponse(
    status: HttpStatus,
    val message: String,
    val stackTrace: String? = null
) {
    val code: Int
    val statu: String

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    val timeStamp: Date

    init {
        code = status.value()
        statu = status.name
        timeStamp = Date()
    }
}