package com.gap.sourcing.smee.steps.user;

import brave.Tracer;
import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.responses.bamboorose.VendorResponse;
import com.gap.sourcing.smee.dtos.responses.bamboorose.VendorResource;
import com.gap.sourcing.smee.dtos.responses.bamboorose.VendorTier;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserVendor;
import com.gap.sourcing.smee.exceptions.GenericBadRequestException;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import com.gap.sourcing.smee.utils.Client;
import com.gap.sourcing.smee.utils.TraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Component
public class SmeeUserBuildVendorRelationStep implements Step {


    @Value("${smee-user-service.vendor-profile-uri}")
    private String vendorProfileUri;

    private static final String USER_NAME = "userName";
    private static final String TRACE_ID = "traceId";

    private final Tracer tracer;

    private final Client client;
    private final Step smeeUserEntityMergeStep;

    public SmeeUserBuildVendorRelationStep(Tracer tracer, Step smeeUserEntityMergeStep, Client client) {
        this.tracer = tracer;
        this.smeeUserEntityMergeStep = smeeUserEntityMergeStep;
        this.client = client;
    }

   @Override
    public Step execute(Context context) throws GenericUserException {
        SmeeUserCreateResource resource = (SmeeUserCreateResource) ((SmeeUserContext) context).getResource();
        SmeeUserContext userContext = (SmeeUserContext) context;
        SmeeUser smeeUser = userContext.getInput();
        String vendorPartyId = resource.getVendorPartyId();
        log.info("Fetching vendors from vendor profile API for user",
                kv(USER_NAME, smeeUser.getUserName()),
                kv("vendorPartyId", vendorPartyId), kv(TRACE_ID, TraceUtil.getTraceId(tracer)));
       VendorResponse vendorData =  client.get(vendorProfileUri+vendorPartyId, VendorResponse.class);
       List<SmeeUserVendor> vendors = createVendorsFromVendorApiResponse(
               vendorData, smeeUser,
               new ArrayList<>(), new HashSet<>());
       if (isVendorsInvalid(vendorData,vendors)){
           log.info("Vendor status is inactive or vendor type is not MFG ", kv(TRACE_ID, TraceUtil.getTraceId(tracer)),
                   kv(USER_NAME, smeeUser.getUserName()));
           throw new GenericBadRequestException(resource, "Vendor Status is not Active or vendor type is not MFG " +
                   "for given vendor party id "
                   + resource.getVendorPartyId());
       }
       if (vendors.isEmpty()) {
           log.info("Vendor details not found for given user",
                   kv(TRACE_ID, TraceUtil.getTraceId(tracer)), kv(USER_NAME, smeeUser.getUserName()));
           throw new GenericBadRequestException(resource, "Vendor details not found for given vendor party id "+
                   resource.getVendorPartyId());
       }
        smeeUser.setVendors(vendors);
        log.info("Retrieved vendors from vendor profile API",kv("vendors", vendors.size()),
                kv(TRACE_ID, TraceUtil.getTraceId(tracer)));
       return smeeUserEntityMergeStep;

    }

    private List<SmeeUserVendor>  createVendorsFromVendorApiResponse(VendorResponse vendorData,
                                                                     SmeeUser smeeUser,
                                                                     List<SmeeUserVendor> vendors,
                                                                     Set<String> vendorPartyIdTracker){

        if (hasVendorInfo(vendorData)) {
            VendorResource vendorResource = vendorData.getResource();
            if(isValidVendor(vendorResource)) {
                vendors.add(buildVendor(vendorResource, smeeUser));
            }
            vendorPartyIdTracker.add(vendorResource.getId());
            return fetchVendorTierVendorsDetails(vendorResource.getVendorTiers(),smeeUser,vendors,vendorPartyIdTracker);
        }
        return vendors;
    }

    private List<SmeeUserVendor> fetchVendorTierVendorsDetails(List<VendorTier> vendorTiers,
                                                               SmeeUser smeeUser,
                                                               List<SmeeUserVendor> vendors,
                                                               Set<String> vendorPartyIdTracker){
        if(containsVendorTier(vendorTiers)){
            vendorTiers.forEach(vendorTier->{
                if(isValidAndNonRepeatedVendor(vendorTier,vendorPartyIdTracker)
                        && StringUtils.equals(vendorTier.getRelationshipStatusDescription(),"ACTIVE")){
                    VendorResponse vendorTierData =  client.get(vendorProfileUri+vendorTier.getId(),
                            VendorResponse.class);
                    createVendorsFromVendorApiResponse(vendorTierData, smeeUser,vendors,vendorPartyIdTracker);
                }
            });
        }
        return vendors;
    }

    private SmeeUserVendor buildVendor(VendorResource vendorResource, SmeeUser smeeUser) {
        return SmeeUserVendor.builder()
                .vendorName(vendorResource.getLegalName())
                .vendorPartyId(vendorResource.getId())
                .userName(smeeUser)
                .build();
    }

    private boolean hasVendorInfo(VendorResponse vendorData){
        return vendorData != null && vendorData.getResource() != null;
    }

    private boolean containsVendorTier(List<VendorTier> vendorTiers){
        return !CollectionUtils.isEmpty(vendorTiers);
    }

    private boolean isValidVendor(VendorResource vendorResource){
        return  StringUtils.equals(vendorResource.getType(),"MFG")
                && StringUtils.equals(vendorResource.getStatus(),"A");
    }

    private boolean isVendorsInvalid(VendorResponse vendorResponse, List<SmeeUserVendor> vendors) {
        return vendors.isEmpty() && vendorResponse != null;
    }

    private boolean isValidAndNonRepeatedVendor(VendorTier vendorTier, Set<String> vendorPartyIdTracker){
        return vendorTier.getId()!= null && !vendorPartyIdTracker.contains(vendorTier.getId());
    }


}
