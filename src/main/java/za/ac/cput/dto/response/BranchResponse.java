package za.ac.cput.dto.response;

import za.ac.cput.enums.Province;

public record BranchResponse(
        Integer branchId,
        String branchName,
        String streetAddress,
        String city,
        Province province,
        String postalCode,
        String phoneNumber,
        String email
) {
}
