package com.robhardt.sak.sakdiscogservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.robhardt.sak.sakdiscogservice.handlers.MasterHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.function.server.router
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter

@Configuration
class ReleaseRoutes() {
    @Bean
    fun apiRouter(releasesHandler: MasterHandler) =
            router {
                GET("/release-stream", releasesHandler::streamAll)
                GET("/", releasesHandler::index)
            }

    @Bean
    fun webSocketHandler(releasesHandler: MasterHandler) : WebSocketHandler {
        return WebSocketHandler{
            session ->
                val om = ObjectMapper()
                session.send(releasesHandler.streamRaw().map {
                    om.writeValueAsString(it)
                }.map {
                    session.textMessage(it)
                })
        }
    }

    @Bean
    fun handlerMapping(releasesHandler: MasterHandler) : HandlerMapping {
        val suhm = SimpleUrlHandlerMapping()
        suhm.order = 10
        suhm.urlMap = mapOf("/ws/release-stream" to webSocketHandler(releasesHandler))
        return suhm
    }

    @Bean
    fun wsha() = WebSocketHandlerAdapter()
}