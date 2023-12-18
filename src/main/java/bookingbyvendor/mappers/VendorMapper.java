package bookingbyvendor.mappers;

import bookingbyvendor.dtos.resources.VendorGetResource;
import bookingbyvendor.entities.Vendor;

public class VendorMapper {
    public static Vendor mapToEntity(VendorGetResource dto) {
        Vendor entity = new Vendor();
        entity.setVendor_num(dto.getVendor_num());
        entity.setVendor_name(dto.getVendor_name());
        entity.setCreated_timestamp(dto.getCreated_timestamp());
        entity.setCreated_by(dto.getCreated_by());
        entity.setLast_modified_timestamp(dto.getLast_modified_timestamp());
        entity.setLast_modified_by(dto.getLast_modified_by());
        entity.setManufacturing_vendor_party_id(dto.getManufacturing_vendor_party_id());
        return entity;
    }

    public static VendorGetResource mapToDto(Vendor entity) {
        return new VendorGetResource(
                entity.getVendor_num(),
                entity.getVendor_name(),
                entity.getCreated_timestamp(),
                entity.getCreated_by(),
                entity.getLast_modified_timestamp(),
                entity.getLast_modified_by(),
                entity.getManufacturing_vendor_party_id()
        );
    }
}
