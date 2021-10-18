package com.gap.sourcing.smee.steps.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;

public class FetchingVendors {


    @Value("${service-uri.denodo-api-service.base}")
    private String denodoURI;

    public void getCurrentPartyId(){
        System.out.println("Getting the Vendors");
        Link currentVendors = Link.of(
                denodoURI + "partyId=1000045"
        ).withSelfRel();



        System.out.println(currentVendors.toString());
    }

}
