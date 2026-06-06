package za.ac.cput.dto.request;

import za.ac.cput.enums.Province;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        @NotBlank(message = "firstName is required") String firstName,
        @NotBlank(message = "lastName is required") String lastName,
        String phoneNumber,
        @Email(message = "email must be a valid address") String email,
        String streetAddress,
        String city,
        @NotBlank(message = "idNumber is required") String idNumber,
        Province province
) {
}
