package com.gap.sourcing.smee.steps.helper;

import com.gap.sourcing.smee.dtos.resources.Resource;
import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.responses.SmeeUserResponse;
import com.gap.sourcing.smee.dtos.responses.SmeeVendor;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserVendor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SmeeUserEntityToDTOConverter {

    public SmeeUserResponse convert(SmeeUser entity, String userType) {
        SmeeUserResponse  response = null;
        if (entity != null) {
            response = new SmeeUserResponse();
            BeanUtils.copyProperties(entity, response);

            List<SmeeVendor> vendors  = entity.getVendors().stream().map(this::mapToVendors)
                    .collect(Collectors.toList());
            response.setUserType(userType);
            response.setVendors(vendors);

        }
        return response;
    }

    private SmeeVendor mapToVendors(SmeeUserVendor vendor) {
        SmeeVendor smeeVendors = new SmeeVendor();
        if (vendor !=  null) {
            BeanUtils.copyProperties(vendor, smeeVendors);
        }
        return smeeVendors;
    }
}
