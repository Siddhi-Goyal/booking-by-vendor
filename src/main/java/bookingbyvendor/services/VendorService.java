package bookingbyvendor.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import bookingbyvendor.dtos.resources.VendorGetResource;
import bookingbyvendor.dtos.responses.VendorResponse;
import bookingbyvendor.entities.Vendor;
import bookingbyvendor.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static bookingbyvendor.mappers.VendorMapper.mapToDto;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public VendorResponse getAllVendors() {
        List<VendorGetResource> vendors = new ArrayList<>();
        List<Vendor> vendorEntities = vendorRepository.findAll();
        for(Vendor entity: vendorEntities){
            VendorGetResource dto = mapToDto(entity);
            vendors.add(dto);
        }
        return new VendorResponse(vendors);
    }

    public Optional<Vendor> getVendor(int id) {
        return vendorRepository.findById(id);
    }

    public void addVendor(Vendor vendor) {
        vendorRepository.save(vendor);
    }

    public void updateVendor(int id, Vendor vendor) {
        vendorRepository.save(vendor);
    }

    public void deleteVendor(int id) {
        vendorRepository.deleteById(id);
    }
}