package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.responses.denodo.DenodoElement;
import com.gap.sourcing.smee.dtos.responses.denodo.DenodoResponse;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserVendor;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.utils.Client;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gap.sourcing.smee.utils.RequestIdGenerator.REQUEST_ID_KEY;
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
                kv("vendorPartyId", vendorPartyId), kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));
        DenodoResponse denodoPartyIdData =  client.get(denodoURI+"partyId="+vendorPartyId, DenodoResponse.class);
        log.info("Vendor data from denodo api for partyId", denodoPartyIdData, kv("partyIdResponse", denodoPartyIdData),
                kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));
        DenodoResponse denodoVendorData =  client.get(denodoURI+"parVenId="+vendorPartyId, DenodoResponse.class);
        log.info("Vendor data from denodo api for parVenId", denodoVendorData, kv("parVenIdResponse", denodoVendorData),
                kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));
        List<SmeeUserVendor> vendors = new ArrayList<>();
        boolean isActiveVendorsAvailable = false;
        if (denodoPartyIdData != null && !CollectionUtils.isEmpty(denodoPartyIdData.getElements()) &&
                isValidVendor(denodoPartyIdData.getElements().get(0))) {
            vendors.add(buildVendor(denodoPartyIdData.getElements().get(0), smeeUser));
            isActiveVendorsAvailable = true;
        }
        if (denodoVendorData != null && !CollectionUtils.isEmpty(denodoVendorData.getElements())) {
            vendors.addAll(denodoVendorData.getElements()
                    .stream()
                    .filter(this::isValidVendor)
                    .map(denodoData ->  buildVendor(denodoData, smeeUser))
                    .collect(Collectors.toList()));

            if(!vendors.isEmpty()) {
                isActiveVendorsAvailable = true;
            }
        }
        if(!isActiveVendorsAvailable & (denodoPartyIdData!=null || denodoVendorData != null)){
            log.info("Vendor Type is not MFG {} ", smeeUser.getUserName(), kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)),
                    kv("userName", smeeUser.getUserName()));
            throw new GenericBadRequestException(resource, "Vendor Status is not Active or vendor type is not MFG for given vendor party id "
                    + resource.getVendorPartyId());

        }
        if (vendors.isEmpty()) {
            log.info("Vendor details not found for given user {} ", smeeUser.getUserName(), kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)),
                    kv("userName", smeeUser.getUserName()));
            throw new GenericBadRequestException(resource, "Vendor details not found for given vendor party id "+ resource.getVendorPartyId());
        }
        smeeUser.setVendors(vendors);
        log.info("Retrieved vendors from denodo API",kv("vendors", vendors.size()), kv(REQUEST_ID_KEY, MDC.get(REQUEST_ID_KEY)));
        return smeeUserEntityMergeStep;
    }

    private boolean isValidVendor(DenodoElement denodoData){
        return denodoData.getVendorType().equalsIgnoreCase("MFG") &&
                denodoData.getStatus().equalsIgnoreCase("Active");
    }

    private SmeeUserVendor buildVendor(DenodoElement denodoElement, SmeeUser smeeUser) {
        return SmeeUserVendor.builder()
                .vendorName(denodoElement.getLegalName())
                .vendorPartyId(denodoElement.getPartyId())
                .userId(smeeUser)
                .build();
    }
}
