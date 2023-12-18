package bookingbyvendor.repositories;

import bookingbyvendor.dtos.resources.VendorGetResource;
import bookingbyvendor.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer>{

}