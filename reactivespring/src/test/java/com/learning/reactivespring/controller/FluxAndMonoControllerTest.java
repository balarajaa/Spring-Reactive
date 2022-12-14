package com.learning.reactivespring.controller;

//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxAndMonoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void flux_approach1() {

        Flux<Integer> streamFlux = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(streamFlux)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete();



    }

    @Test
    public void flux_approach2() {
        webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Integer.class)
                .hasSize(4);


    }

    @Test
    public void flux_approach3() {

        List<Integer> expectedResults = Arrays.asList(1,2,3,4);
        EntityExchangeResult<List<Integer>> listEntityExchangeResult =
                webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();

        assertEquals(expectedResults, listEntityExchangeResult.getResponseBody());

    }

    @Test
    public void flux_approach4() {

        List<Integer> expectedResults = Arrays.asList(1,2,3,4);
                webTestClient.get().uri("/flux")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(Integer.class)
                        .consumeWith((response) -> {
                            assertEquals(expectedResults, response.getResponseBody());
                        });

    }

    @Test
    public void fluxStream() {

        Flux<Long> longStreamFlux = webTestClient.get().uri("/fluxstream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(longStreamFlux)
                .expectNext(0l)
                .expectNext(1l)
                .expectNext(2l)
                .thenCancel()
                .verify();


    }

    @Test
    public void mono() {
        webTestClient.get().uri("/mono")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .consumeWith((response) -> {

                    assertEquals(new Integer(1), response.getResponseBody());
                        });
    }

    }