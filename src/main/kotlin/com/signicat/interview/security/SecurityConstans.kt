package com.signicat.interview.security

val SIGN_UP_URL = "/user/signup"
val SECRET = "SecretKeyToGenJWTs"
val TOKEN_PREFIX = "Bearer "
val HEADER_STRING = "Authorization"
val EXPIRATION_TIME: Long = 864_000_000 // 10 days