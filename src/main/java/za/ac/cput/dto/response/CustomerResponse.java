package za.ac.cput.dto.response;

import za.ac.cput.enums.Province;

public record CustomerResponse(
        Integer customerId,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String streetAddress,
        String city,
        String idNumber,
        Province province
) {
}
