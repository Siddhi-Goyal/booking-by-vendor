package com.gap.sourcing.smee.services.impl;

import com.gap.sourcing.smee.entities.SmeeUserType;
import com.gap.sourcing.smee.exceptions.GenericUserException;
import com.gap.sourcing.smee.repositories.SmeeUserTypeRepository;
import com.gap.sourcing.smee.services.SmeeUserTypeLoadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SmeeUserTypeLoadServiceTest {

    @Mock
    private SmeeUserTypeRepository smeeUserTypeRepository;

    private SmeeUserTypeLoadService smeeUserTypeLoadService;

    List<SmeeUserType> entity;

    @BeforeEach
    public void init() {

        entity = new ArrayList<SmeeUserType>();
        SmeeUserType sut = new SmeeUserType();
        sut.setUserType("GIS PD");
        sut.setDescription("Test");
        entity.add(sut);

        smeeUserTypeLoadService = new SmeeUserTypeLoadServiceImpl(smeeUserTypeRepository);
    }
    @Test
    void execute_shouldReturnASmeeUserType() throws GenericUserException {
        when(smeeUserTypeRepository.findAll()).thenReturn(entity);
        List<SmeeUserType> response = smeeUserTypeLoadService.getSmeeUserTypes();

        assertThat(response, is(notNullValue()));
    }

}
