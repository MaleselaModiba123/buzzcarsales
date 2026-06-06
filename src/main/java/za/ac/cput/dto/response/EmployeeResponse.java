package za.ac.cput.dto.response;

import za.ac.cput.enums.JobTitle;
import java.time.LocalDate;

public record EmployeeResponse(
        Integer employeeId,
        String firstName,
        String lastName,
        String idNumber,
        String phoneNumber,
        String email,
        JobTitle jobTitle,
        LocalDate hireDate,
        BranchResponse branch
) {
}
