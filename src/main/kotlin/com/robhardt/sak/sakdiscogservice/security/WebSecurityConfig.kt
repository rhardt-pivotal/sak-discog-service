package com.robhardt.sak.sakdiscogservice.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.PreauthMapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.anyExchange
import org.springframework.security.config.annotation.web.builders.HttpSecurity




@Configuration
@Profile("cloud")
@EnableWebFluxSecurity
class WebSecurityConfig(@Value("\${auth.app.guid:Hereis}") val authAppGuid: String) {



    val logger : Logger = LoggerFactory.getLogger(WebSecurityConfig::class.java)

    @Bean
    @Throws(Exception::class)
    fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {

        return http.authorizeExchange()
                .pathMatchers("/**").hasAuthority("ROLE_USER")
                .anyExchange()
                .authenticated()
                .and().x509()
                .subjectPrincipalRegex("OU=app:(.*?)(?:,|$)")
                .and().build()
    }


    @Bean
    fun userDetailsRepository(): PreauthMapReactiveUserDetailsService {
        logger.info("**********************UDREP")
        logger.info(authAppGuid)

        val userBuilder = User.withDefaultPasswordEncoder()
        val app = userBuilder.username(authAppGuid).password("notused").roles("USER", "ADMIN").build()
        return LoggingMapReactiveUDS(app)
    }



}