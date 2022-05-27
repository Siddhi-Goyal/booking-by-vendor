package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserVendor;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
public class SmeeUserEntityMergeStep implements Step {

    private final  Step smeeUserPersistStep;

    public SmeeUserEntityMergeStep(Step smeeUserPersistStep) {
        this.smeeUserPersistStep = smeeUserPersistStep;
    }

    @Override
    public Step execute(Context context) throws GenericUserException {
        SmeeUserContext smeeUserContext = (SmeeUserContext) context;
        SmeeUser input = smeeUserContext.getInput();
        SmeeUser current = smeeUserContext.getCurrent();

        log.info("Merging record's created from context's current to context's input attribute, " +
                "input={}, current={}", input, current);
        if (current != null) {
            current.setLastModifiedBy(input.getLastModifiedBy());
            current.setLastModifiedDate(input.getLastModifiedDate());
            current.setUserEmail(input.getUserEmail());
            current.setIsVendor(input.getIsVendor());
            current.setUserTypeId(input.getUserTypeId());
            current.setFirstName(input.getFirstName());
            current.setLastName(input.getLastName());
            current.setIsActive(input.getIsActive()!=null?input.getIsActive():current.getIsActive());
            if (Boolean.TRUE.equals(input.getIsVendor())) {
                List<SmeeUserVendor> removedVendors = current.getVendors().stream().filter(vendor ->
                        isPresent(input.getVendors(),
                        vendor)).collect(Collectors.toList());
                List<SmeeUserVendor> addedVendors = input.getVendors().stream().filter(vendor ->
                        isPresent(current.getVendors(),
                        vendor)).collect(Collectors.toList());
                setVendorDetails(current, removedVendors, addedVendors);
            }
        }

        log.info("Merged record's created details from context's current to context's input attribute");

        return smeeUserPersistStep;
    }

    private void setVendorDetails(SmeeUser current, List<SmeeUserVendor> removedVendors,
                                  List<SmeeUserVendor> addedVendors) {
        if (!removedVendors.isEmpty()) {
            removedVendors.forEach(vendor -> current.getVendors().remove(vendor));
        }
        if (!addedVendors.isEmpty()) {
            addedVendors.forEach(vendor -> {
                vendor.setUserName(current);
                current.getVendors().add(vendor);
            });
        }
    }

    private boolean isPresent(List<SmeeUserVendor> vendors, SmeeUserVendor vendor) {
        return vendors.stream().noneMatch(ven -> vendor.getVendorName().equals(ven.getVendorName()) &&
                vendor.getVendorPartyId().equals(ven.getVendorPartyId()));
    }
}
