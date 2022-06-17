package com.signicat.interview.security

import com.nimbusds.jose.crypto.ECDSAVerifier
import com.nimbusds.jose.jwk.Curve.P_256
import com.nimbusds.jose.jwk.gen.ECKeyGenerator
import com.nimbusds.jwt.SignedJWT
import com.signicat.interview.domain.Subject
import com.signicat.interview.domain.UserGroup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant.now
import java.util.*
import java.util.Date.from

class TokenFactoryTest {

    private val key = ECKeyGenerator(P_256)
        .keyID("0")
        .generate()

    private val underTest = TokenFactory(key)
    private val user=Subject(1,"saman","sas", Collections.singleton(UserGroup(1,"TEST")))

    @Test
    fun `generate token`() {
        val token = underTest.generate(user)

        val signedJWT = SignedJWT.parse(token)

        assertEquals(
            true,
            signedJWT.jwtClaimsSet.expirationTime.after(
                from(now())
            )
        )
    }

    @Test
    fun `verify token`() {
        val token = underTest.generate(user)

        val signedJWT = SignedJWT.parse(token)

        assertEquals(
            true,
            signedJWT.verify(
                ECDSAVerifier(key.toECPublicKey())
            )
        )
    }
}
