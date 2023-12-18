package bookingbyvendor.contollers;

import bookingbyvendor.controllers.VendorController;
import bookingbyvendor.dtos.responses.VendorResponse;
import bookingbyvendor.entities.Vendor;
import bookingbyvendor.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class VendorControllerTest {

    private MockMvc mockMvc;

    //to create a mock object
    @Mock
    private VendorService vendorService;

    //to inject the mocked objects into that field
    //class under test
    @InjectMocks
    private VendorController vendorController;

    //to execute the following statement before all test cases
    @BeforeEach
    //initialize all the fields that have been mocked up
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllVendorsEndPointSuccess() throws Exception {
        //mock response for successful scenario
        VendorResponse response = new VendorResponse(Collections.emptyList());
        when(vendorService.getAllVendors()).thenReturn(response);

        VendorResponse result = vendorController.getAllVendors();

        assertEquals(response, result);
        //mock object has been called exactly once during test execution
        verify(vendorService, times(1)).getAllVendors();
        //no further interactions with the vendorService mocked object beyond the explicitly verified methods
        verifyNoMoreInteractions(vendorService);

        mockMvc.perform(get("/vendors"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllVendorsEndpointNoVendors() throws Exception {
        // Prepare a mock response for scenario where no vendors are found
        when(vendorService.getAllVendors()).thenReturn(null); // Simulating no vendors found

        // Perform the mock HTTP GET request to the endpoint
        mockMvc.perform(get("/vendors"))
                .andExpect(status().isNotFound()); // Assert HTTP status code is Not Found (404)
    }

    @Test
    public void testGetAllVendorsEndpointServerError() throws Exception {
        // Simulate a scenario where an unexpected error occurs
        when(vendorService.getAllVendors()).thenThrow(new RuntimeException("Internal Server Error"));

        // Perform the mock HTTP GET request to the endpoint
        mockMvc.perform(get("/vendors"))
                .andExpect(status().isInternalServerError()); // Assert HTTP status code is Internal Server Error (500)
    }

    @Test
    public void testGetVendor() {
        int vendorId = 1;
        Vendor vendor = new Vendor(); // Create a sample vendor for testing
        when(vendorService.getVendor(vendorId)).thenReturn(java.util.Optional.of(vendor));

        Vendor result = vendorController.getVendor(vendorId);

        assertEquals(vendor, result);
        verify(vendorService, times(1)).getVendor(vendorId);
        verifyNoMoreInteractions(vendorService);
    }

    @Test
    public void testAddVendor() {
        Vendor vendorToAdd = new Vendor(); // Create a sample vendor to add
        doNothing().when(vendorService).addVendor(any(Vendor.class));

        vendorController.addVendor(vendorToAdd);

        verify(vendorService, times(1)).addVendor(vendorToAdd);
        verifyNoMoreInteractions(vendorService);
    }

    @Test
    public void testUpdateVendor() {
        int vendorId = 1;
        Vendor vendorToUpdate = new Vendor(); // Create a sample vendor to update

        doNothing().when(vendorService).updateVendor(eq(vendorId), any(Vendor.class));

        vendorController.updateVendor(vendorToUpdate, vendorId);

        verify(vendorService, times(1)).updateVendor(eq(vendorId), any(Vendor.class));
        verifyNoMoreInteractions(vendorService);
    }

    @Test
    public void testDeleteVendor() {
        int vendorId = 1;
        doNothing().when(vendorService).deleteVendor(vendorId);

        vendorController.deleteVendor(vendorId);

        verify(vendorService, times(1)).deleteVendor(vendorId);
        verifyNoMoreInteractions(vendorService);
    }
}
