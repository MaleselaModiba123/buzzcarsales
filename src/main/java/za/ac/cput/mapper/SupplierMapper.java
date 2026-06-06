package za.ac.cput.mapper;

import za.ac.cput.domain.Supplier;
import za.ac.cput.dto.request.SupplierRequest;
import za.ac.cput.dto.response.SupplierResponse;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public Supplier toEntity(SupplierRequest r) {
        Supplier s = new Supplier();
        s.setSupplierName(r.supplierName());
        s.setContactPerson(r.contactPerson());
        s.setPhoneNumber(r.phoneNumber());
        s.setEmail(r.email());
        s.setStreetAddress(r.streetAddress());
        s.setCity(r.city());
        s.setProvince(r.province());
        s.setPostalCode(r.postalCode());
        return s;
    }

    public SupplierResponse toResponse(Supplier s) {
        if (s == null) {
            return null;
        }
        return new SupplierResponse(
                s.getSupplierId(), s.getSupplierName(), s.getContactPerson(), s.getPhoneNumber(),
                s.getEmail(), s.getStreetAddress(), s.getCity(), s.getProvince(), s.getPostalCode());
    }
}
