package com.gap.sourcing.smee.steps.helper;

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

    public SmeeUserResponse convert(SmeeUser entity) {
        SmeeUserResponse  response = null;
        if (entity != null) {
            response = new SmeeUserResponse();
            BeanUtils.copyProperties(entity, response);

            List<SmeeVendor> vendors  = entity.getVendors().stream().map(vendor -> mapToVendors(vendor))
                    .collect(Collectors.toList());
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
