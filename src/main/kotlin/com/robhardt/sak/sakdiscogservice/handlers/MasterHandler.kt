package com.robhardt.sak.sakdiscogservice.handlers


import com.robhardt.sak.sakdiscogservice.MasterRepository
import org.apache.commons.logging.LogFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.atomic.AtomicInteger



@Component
class MasterHandler(val releaseRepository: MasterRepository) {

        val logger = LogFactory.getLog(MasterHandler::class.java)



        fun streamAll(serverRequest: ServerRequest): Mono<ServerResponse> {
                val counter = AtomicInteger(0)
                return  ok().contentType(
                        MediaType.TEXT_EVENT_STREAM)
                        .body(releaseRepository.findAll().map({
                                r -> r.index = counter.incrementAndGet();
                                r;
                        }).doOnNext { r -> if (counter.get() % 100 == 0) logger.debug(counter.get()) }, Release::class.java)
        }

        fun streamRaw() : Flux<Release> {
                val counter = AtomicInteger(0)
                return releaseRepository.findAll().map({
                        r -> r.index = counter.incrementAndGet();
                        r;
                }).doOnNext { r -> if (counter.get() % 100 == 0) logger.debug(counter.get()) }
        }

        fun index(serverRequest: ServerRequest): Mono<ServerResponse> {
                return ok().contentType(
                        MediaType.TEXT_HTML).body(Mono.just("Hello World"), String::class.java)
                        .doOnNext { r -> logger.debug(serverRequest.headers()) }
        }




}