package bookingbyvendor.dtos.responses;

import bookingbyvendor.dtos.resources.VendorGetResource;

import java.util.List;


public class VendorResponse {
    List<VendorGetResource> vendors;

    public VendorResponse() {
    }

    public VendorResponse(List<VendorGetResource> vendors) {
        this.vendors = vendors;
    }

    public List<VendorGetResource> getVendors() {
        return vendors;
    }

    public void setVendors(List<VendorGetResource> vendors) {
        this.vendors = vendors;
    }
}
