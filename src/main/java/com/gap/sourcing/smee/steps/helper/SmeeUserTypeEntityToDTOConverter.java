package com.gap.sourcing.smee.steps.helper;

import com.gap.sourcing.smee.dtos.responses.SmeeUserTypeResponse;
import com.gap.sourcing.smee.dtos.responses.SmeeUserTypes;
import com.gap.sourcing.smee.entities.SmeeUserType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SmeeUserTypeEntityToDTOConverter {

    public SmeeUserTypeResponse convert(List<SmeeUserType> entity) {
        SmeeUserTypeResponse  response = null;
        if (entity != null) {
            response = new SmeeUserTypeResponse();
            List<SmeeUserTypes> smeeUserTypes  = entity.stream().map(this::mapToUserTypes).collect(Collectors.toList());
            response.setUserTypes(smeeUserTypes);

        }
        return response;
    }

    private SmeeUserTypes mapToUserTypes(SmeeUserType userType) {
        SmeeUserTypes smeeUserTypes = new SmeeUserTypes();
        if (userType !=  null) {
            BeanUtils.copyProperties(userType, smeeUserTypes);
            smeeUserTypes.setName(userType.getUserType());
        }
        return smeeUserTypes;
    }

}
