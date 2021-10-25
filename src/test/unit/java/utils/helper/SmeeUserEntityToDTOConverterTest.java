package utils.helper;

import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.responses.SmeeUserResponse;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserVendor;
import com.gap.sourcing.smee.steps.helper.SmeeUserEntityToDTOConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith({MockitoExtension.class})
class SmeeUserEntityToDTOConverterTest {

    @InjectMocks
    private SmeeUserEntityToDTOConverter smeeUserEntityToDTOConverter;

    private static SmeeUser entity;
    private static SmeeUserCreateResource smeeUserCreateResource;


    @BeforeAll
    static void classInit() {
        entity = new SmeeUser();
        SmeeUserVendor vendor = SmeeUserVendor.builder()
                .vendorName("test vendor")
                .vendorPartyId("test id")
                .build();
        entity.setVendors(List.of(vendor));
        smeeUserCreateResource = new SmeeUserCreateResource();
    }

    @Test
    void convert_ShouldReturnObject() {
        SmeeUserResponse smeeUserResponse = smeeUserEntityToDTOConverter.convert(entity, smeeUserCreateResource);
        assertThat(smeeUserResponse, is(notNullValue()));
        assertEquals(1, smeeUserResponse.getVendors().size());
    }
}
