package za.ac.cput.dto.response;

import za.ac.cput.enums.Province;

public record SupplierResponse(
        Integer supplierId,
        String supplierName,
        String contactPerson,
        String phoneNumber,
        String email,
        String streetAddress,
        String city,
        Province province,
        String postalCode
) {
}
