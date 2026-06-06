package za.ac.cput.dto.request;

import za.ac.cput.enums.Province;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record BranchRequest(
        @NotBlank(message = "branchName is required") String branchName,
        String streetAddress,
        String city,
        Province province,
        String postalCode,
        String phoneNumber,
        @Email(message = "email must be a valid address") String email
) {
}
