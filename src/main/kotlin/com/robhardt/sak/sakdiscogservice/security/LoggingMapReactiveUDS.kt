package com.robhardt.sak.sakdiscogservice.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.PreauthMapReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono
import kotlin.math.log

class LoggingMapReactiveUDS(users: UserDetails) : PreauthMapReactiveUserDetailsService(users) {

    val logger : Logger = LoggerFactory.getLogger(LoggingMapReactiveUDS::class.java)

    override fun findByUsername(username: String): Mono<UserDetails> {

        logger.debug("** Checking username: "+username);

        val ret = super.findByUsername(username)

        return ret
    }

}

