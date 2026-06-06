package za.ac.cput.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CarMakeRequest(
        @NotBlank(message = "makeName is required") String makeName,
        String originCountry
) {
}
