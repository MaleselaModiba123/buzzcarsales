package za.ac.cput.dto.request;

import za.ac.cput.enums.JobTitle;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record EmployeeRequest(
        @NotNull(message = "branchId is required") Integer branchId,
        @NotBlank(message = "firstName is required") String firstName,
        @NotBlank(message = "lastName is required") String lastName,
        @NotBlank(message = "idNumber is required") String idNumber,
        String phoneNumber,
        @Email(message = "email must be a valid address") String email,
        JobTitle jobTitle,
        LocalDate hireDate
) {
}
