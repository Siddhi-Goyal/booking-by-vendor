package com.gap.sourcing.smee.steps.user;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.contexts.SmeeUserContext;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserVendor;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserVendorRepository;
import com.gap.sourcing.smee.steps.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SmeeUserEntityMergeStep implements Step {

    private final  Step smeeUserPersistStep;
    private final SmeeUserVendorRepository  smeeUserVendorRepository;

    public SmeeUserEntityMergeStep(Step smeeUserPersistStep, SmeeUserVendorRepository smeeUserVendorRepository) {
        this.smeeUserPersistStep = smeeUserPersistStep;
        this.smeeUserVendorRepository = smeeUserVendorRepository;
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
            List<SmeeUserVendor> removedVendors = current.getVendors().stream().filter(vendor  -> isPresent(input.getVendors(),
                    vendor)).collect(Collectors.toList());
            List<SmeeUserVendor> addedVendors = input.getVendors().stream().filter(vendor  -> isPresent(current.getVendors(),
                    vendor)).collect(Collectors.toList());
            if (!removedVendors.isEmpty())  {
                removedVendors.forEach(vendor -> {
                    smeeUserVendorRepository.delete(vendor);
                    current.getVendors().remove(vendor);
                });
            }
            if (!addedVendors.isEmpty()) {
                addedVendors.forEach(vendor -> {
                    vendor.setUserId(current);
                    SmeeUserVendor user = smeeUserVendorRepository.save(vendor);
                    current.getVendors().add(user);
                });
            }
        }

        log.info("Merged record's created details from context's current to context's input attribute");

        return smeeUserPersistStep;
    }

    private boolean isPresent(List<SmeeUserVendor> vendors, SmeeUserVendor vendor) {
        return vendors.stream().noneMatch(ven -> vendor.getVendorName().equals(ven.getVendorName()) &&
                vendor.getUserId().equals(ven.getUserId()));
    }
}
