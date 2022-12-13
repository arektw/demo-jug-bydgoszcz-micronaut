package com.huuuge.demojug

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class JugDemoMicronautSpec extends Specification {

    @Inject
    EmbeddedApplication<?> application

    @Inject
    @Client("/")
    HttpClient httpClient

    static final String GREETING_URI = "/greeting"
    static final String TEST_GREETING = "Welcome in test!"


    void 'test it works'() {
        expect:
        application.running
    }

    def "should return configured greeting"() {
        when: "call for greetings is made"
        def response = httpClient.toBlocking()
                .retrieve(HttpRequest.GET(GREETING_URI))

        then: "test greeting returned"
        response == TEST_GREETING
    }

    def "should return greeting for requested city"() {
        given:
        def requestedCity = "Elk"

        when: "call for greetings is made"
        def response = httpClient.toBlocking()
                .retrieve(HttpRequest.GET(GREETING_URI + "/" + requestedCity))


        then: "test greeting returned"
        response == "Hello " + requestedCity + "!"
    }

    def "should return bad request for city not matching expected size"() {
        when: "call for greetings is made"
        HttpResponse response = httpClient.toBlocking()
                .exchange(HttpRequest.GET(GREETING_URI + "/" + requestedCity))

        then:
        HttpClientResponseException ex = thrown()

        and: "validation error occur"
        ex.status == HttpStatus.BAD_REQUEST

        where:
        requestedCity << ["12", "parametrizations"]
    }
}
