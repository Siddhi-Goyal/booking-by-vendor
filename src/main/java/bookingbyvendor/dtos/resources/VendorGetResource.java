package bookingbyvendor.dtos.resources;

public class VendorGetResource implements Resource {
    private int vendor_num;
    private String vendor_name;
    private String created_timestamp;
    private String created_by;
    private String last_modified_timestamp;
    private String last_modified_by;
    private String manufacturing_vendor_party_id;

    //Methods
    //No Args Constructor
    public VendorGetResource() {
    }

    //Args Constructor
    public VendorGetResource(int vendor_num, String vendor_name, String created_timestamp, String created_by, String last_modified_timestamp, String last_modified_by, String manufacturing_vendor_party_id) {
        this.vendor_num = vendor_num;
        this.vendor_name = vendor_name;
        this.created_timestamp = created_timestamp;
        this.created_by = created_by;
        this.last_modified_timestamp = last_modified_timestamp;
        this.last_modified_by = last_modified_by;
        this.manufacturing_vendor_party_id = manufacturing_vendor_party_id;
    }

    public int getVendor_num() {
        return vendor_num;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public String getCreated_timestamp() {
        return created_timestamp;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getLast_modified_timestamp() {
        return last_modified_timestamp;
    }

    public String getLast_modified_by() {
        return last_modified_by;
    }

    public String getManufacturing_vendor_party_id() {
        return manufacturing_vendor_party_id;
    }

    public void setVendor_num(int vendor_num) {
        this.vendor_num = vendor_num;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public void setCreated_timestamp(String created_timestamp) {
        this.created_timestamp = created_timestamp;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }


    public void setLast_modified_timestamp(String last_modified_timestamp) {
        this.last_modified_timestamp = last_modified_timestamp;
    }

    public void setLast_modified_by(String last_modified_by) {
        this.last_modified_by = last_modified_by;
    }

    public void setManufacturing_vendor_party_id(String manufacturing_vendor_party_id) {
        this.manufacturing_vendor_party_id = manufacturing_vendor_party_id;
    }
}
