package za.ac.cput.dto.request;

import za.ac.cput.enums.Province;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SupplierRequest(
        @NotBlank(message = "supplierName is required") String supplierName,
        String contactPerson,
        String phoneNumber,
        @Email(message = "email must be a valid address") String email,
        String streetAddress,
        String city,
        Province province,
        String postalCode
) {
}
