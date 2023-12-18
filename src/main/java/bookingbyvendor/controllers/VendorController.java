package bookingbyvendor.controllers;

import bookingbyvendor.dtos.responses.VendorResponse;
import bookingbyvendor.entities.Vendor;
import bookingbyvendor.services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @RequestMapping("/vendors")
    public VendorResponse getAllVendors() {
        return vendorService.getAllVendors();
    }

    @RequestMapping("/vendors/{id}")
    public Vendor getVendor(@PathVariable int id) {
        return vendorService.getVendor(id).get();
    }

    @RequestMapping(method = RequestMethod.POST, value="/vendors")
    public void addVendor(@RequestBody Vendor vendor) {
        vendorService.addVendor(vendor);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/vendors/{id}")
    public void updateVendor(@RequestBody Vendor vendor, @PathVariable int id) {
        vendorService.updateVendor(id, vendor);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/vendors/{id}")
    public void deleteVendor(@PathVariable int id) {
        vendorService.deleteVendor(id);
    }
}