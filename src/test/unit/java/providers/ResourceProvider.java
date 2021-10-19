package providers;

import com.gap.sourcing.smee.dtos.resources.SmeeUserCreateResource;

public class ResourceProvider {



    public static SmeeUserCreateResource getSmeeUserCreateResource(){
        return new SmeeUserCreateResource()
                .builder()
                .userName("Vpl6e5i")
                .userEmail
        ("kausher@arvindexports.com")
                .userType("Garment Vendor")
                .isVendor(true)
                .userId("vprabhu").build();
    }
}
