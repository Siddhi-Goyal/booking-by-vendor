package utils;

import com.gap.sourcing.smee.dtos.responses.denodo.DenodoResponse;
import com.gap.sourcing.smee.exceptions.ApiClientException;
import com.gap.sourcing.smee.utils.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ClientTest {

    private static final String DENODO_URL="http://denodo.gapinc.com:9090/server/sourcing_db/vendor_relationship/views/vendor_relationship?$format=json&partyId=1000045";
    private Client client;
    private WebClient webClient;
    private WebClient.Builder webClientBuilder;
    private ClientResponse clientResponse;

    @Test
    void get_Success_GlobalAssortmentResponse() {
        clientResponse =ClientResponse.create(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(getSuccessResponsepayload()).build();
        webClientBuilder = WebClient.builder().exchangeFunction((request) -> Mono.just(clientResponse));
        webClient = webClientBuilder.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
        client = new Client(webClientBuilder);
        DenodoResponse response = client.get(DENODO_URL, DenodoResponse.class);
        assertEquals("vendor_relationship",response.getName());
        assertEquals(1, response.getElements().size());
    }

    @Test
    void get_GlobalAssortmentResponse_throw_exception() {
        clientResponse =ClientResponse.create(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(getInvalidResponsePayload()).build();
        webClientBuilder = WebClient.builder().exchangeFunction((request) -> Mono.just(clientResponse));
        webClient = webClientBuilder.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
        client = new Client(webClientBuilder);
        Assertions.assertThrows(ApiClientException.class, () -> client.get(DENODO_URL, DenodoResponse.class));
    }

    private String getSuccessResponsepayload(){
        return "{\"name\":\"vendor_relationship\",\"elements\":[{\"parVenId\":\"1002493\",\"relTypeCode\":\"VND2VND\",\"sysEntState\":\"DELETED\",\"vendorType\":\"MFG\",\"status\":\"Active\",\"isChildVen\":\"0\",\"relMgr\":null,\"partyId\":\"1000045\",\"aliasName\":\"THE ARVIND00045\",\"countryOfOrigin\":\"INDIA\",\"legalName\":\"ARVIND LIMITED\",\"orgType\":\"Vendor\"}],\"links\":[{\"rel\":\"self\",\"href\":\"http://denodo.gapinc.com:9090/server/sourcing_db/vendor_relationship/views/vendor_relationship?$format=json&partyId=1000045\"}]}";
    }

    private String getInvalidResponsePayload(){
        return "{\"httpStatus\":200,\"requestId\":\"HVWAYADElTnfMWId\",\"resource\":{\"id\":\"c9c2e5a9-f210-4e70-aee0-a05e0a5a891f\",\"deleted\":false,\"isCustomerChoiceActive\":true,\"bomCustomerChoice\":{\"bomCCNumber\":\"000002423722\"},\"customerChoice\":{\"number\":\"975498006\",\"comments\":null},\"se22Color\":{\"colorCode\":\"839\",\"colorAbbrDescription\":\"STMGNFDR\",\"colorLongDescription\":\"STEM GREEN FEEDER\"},\"finishedGoodColor\":null,\"colorComment\":null,\"comments\":null,\"productionComments\":null,\"realizeComments\":null}";
    }
}