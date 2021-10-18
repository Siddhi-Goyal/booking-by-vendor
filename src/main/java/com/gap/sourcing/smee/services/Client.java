package com.gap.sourcing.smee.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
public class Client {

    @Value("${service-uri.denodo-api-service.base}")
    private String denodoURI;

    private final WebClient webClient;

    public Client(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder
                .baseUrl("http://denodo.gapinc.com:9090")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }
    public String get(String uri) {
        WebClient.RequestBodySpec request = webClient.
                method(HttpMethod.GET)
                .uri(builder -> builder.path(uri).queryParam("partyId","100045").build())
               ;

        return processRequest(request, HttpMethod.GET, null).block();
    }

    private <T, Q> Mono<String> processRequest(WebClient.RequestBodySpec request, HttpMethod method, Q requestBody ) {
        return request.
                body(BodyInserters.fromValue((Objects.nonNull(requestBody) ? requestBody : "")))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorMap(error -> handleError(error, method));
    }

    private Throwable handleError(Throwable throwable, HttpMethod method) {
        if (throwable instanceof WebClientResponseException) {
            WebClientResponseException wbException = (WebClientResponseException) throwable;
            return new Exception(wbException.getMessage());
        }

        return new Exception(throwable.getMessage());
    }
}
