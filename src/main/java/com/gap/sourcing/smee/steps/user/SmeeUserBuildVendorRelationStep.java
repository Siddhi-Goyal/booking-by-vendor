package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.responses.denodo.DenodoElement;
import com.gap.sourcing.smee.dtos.responses.denodo.DenodoResponse;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserVendor;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.utils.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Component
public class SmeeUserBuildVendorRelationStep implements Step {


    @Value("${smee-user-service.denodo-uri}")
    private String denodoURI;


    private final Client client;
    private final Step smeeUserEntityMergeStep;

    public SmeeUserBuildVendorRelationStep(Step smeeUserEntityMergeStep, Client client) {
        this.smeeUserEntityMergeStep = smeeUserEntityMergeStep;
        this.client = client;
    }


    @Override
    public Step execute(Context context) throws GenericUserException {
        SmeeUserCreateResource resource = (SmeeUserCreateResource) ((SmeeUserContext) context).getResource();
        SmeeUserContext userContext = (SmeeUserContext) context;
        SmeeUser smeeUser = userContext.getInput();
        String vendorPartyId = resource.getVendorPartyId();
        log.info("Fetching vendors from denodo API for user {}", smeeUser.getUserName(),kv("userName", smeeUser.getUserName()),
                kv("vendorPartyId", vendorPartyId));
        DenodoResponse denodoPartyIdData =  client.get(denodoURI+"partyId="+vendorPartyId, DenodoResponse.class);
        log.info("Vendor data from denodo api for partyId", denodoPartyIdData, kv("partyIdResponse", denodoPartyIdData));
        DenodoResponse denodoVendorData =  client.get(denodoURI+"parVenId="+vendorPartyId, DenodoResponse.class);
        log.info("Vendor data from denodo api for parVenId", denodoVendorData, kv("parVenIdResponse", denodoVendorData));
        List<SmeeUserVendor> vendors = new ArrayList<>();
        if (denodoPartyIdData != null && !CollectionUtils.isEmpty(denodoPartyIdData.getElements())) {
            vendors.add(buildVendor(denodoPartyIdData.getElements().get(0), vendorPartyId, smeeUser));
        }
        if (denodoVendorData != null && !CollectionUtils.isEmpty(denodoVendorData.getElements())) {
            vendors.addAll(denodoVendorData.getElements().stream().map(denodoData -> buildVendor(denodoData,
                    vendorPartyId, smeeUser)).collect(Collectors.toList()));
        }
        smeeUser.setVendors(vendors);
        log.info("Retrieved vendors from denodo API",kv("vendors", vendors.size()));
        return smeeUserEntityMergeStep;
    }

    private SmeeUserVendor buildVendor(DenodoElement denodoElement, String vendorPartyId, SmeeUser smeeUser) {
        return SmeeUserVendor.builder()
                .vendorName(denodoElement.getLegalName())
                .vendorPartyId(vendorPartyId)
                .userId(smeeUser)
                .build();
    }
}
