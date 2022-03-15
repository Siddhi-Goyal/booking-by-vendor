package com.gap.sourcing.smee.utils;

import com.gap.sourcing.smee.exceptions.ApiClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class Client {

    @Value("${smee-user-service.apikey}")
    private String apikey;

    private static final String HEADER_API_KEY = "apiKey";

    private final WebClient webClient;

    public Client(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }

    public <T> T get(String uri, Class<T> responseClass) {
        WebClient.RequestBodySpec request = webClient.method(HttpMethod.GET).uri(uri);
        return processRequest(request, HttpMethod.GET, null, responseClass).block();
    }

    private <T, Q> Mono<T> processRequest(WebClient.RequestBodySpec request, HttpMethod method,
                                          Q requestBody, Class<T> responseClass) {
        return request.
                header(HEADER_API_KEY, apikey).
                body(BodyInserters.fromValue((Objects.nonNull(requestBody) ? requestBody : "")))
                .retrieve()
                .bodyToMono(responseClass)
                .onErrorMap(error -> handleError(error, method));
    }

    private Throwable handleError(Throwable throwable, HttpMethod method) {
        if (throwable instanceof WebClientResponseException) {
            WebClientResponseException wbException = (WebClientResponseException) throwable;
            return new ApiClientException(wbException.getMessage());
        }

        return new ApiClientException(throwable.getMessage());
    }
}
