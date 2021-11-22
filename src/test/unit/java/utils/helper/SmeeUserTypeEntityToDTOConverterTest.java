package utils.helper;

import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;
import com.gap.sourcing.smee.dtos.responses.SmeeUserResponse;
import com.gap.sourcing.smee.dtos.responses.SmeeUserTypeResponse;
import com.gap.sourcing.smee.entities.SmeeUser;
import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.entities.SmeeUserVendor;
import com.gap.sourcing.smee.steps.helper.SmeeUserEntityToDTOConverter;
import com.gap.sourcing.smee.steps.helper.SmeeUserTypeEntityToDTOConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith({MockitoExtension.class})
class SmeeUserTypeEntityToDTOConverterTest {

    @InjectMocks
    private SmeeUserTypeEntityToDTOConverter smeeUserTypeEntityToDTOConverter;

    private static List<SmeeUserType> entity;
    private static SmeeUserCreateResource smeeUserCreateResource;

    @BeforeAll
    static void classInit() {
        entity = new ArrayList<SmeeUserType>();
        SmeeUserType sut = new SmeeUserType();
        sut.setUserType("GIS PD");
        sut.setDescription("Test");
        entity.add(sut);
    }

    @Test
    void convert_ShouldReturnObject() {
        SmeeUserTypeResponse smeeUserTypeResponse = smeeUserTypeEntityToDTOConverter.convert(entity);
        assertThat(smeeUserTypeResponse, is(notNullValue()));
        assertEquals(1, smeeUserTypeResponse.getUserTypes().size());
    }
}
