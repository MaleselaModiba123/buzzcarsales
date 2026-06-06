package za.ac.cput.dto.response;

import za.ac.cput.enums.PaymentMethod;
import java.time.LocalDate;

public record SaleResponse(
        Integer saleId,
        LocalDate saleDate,
        Double salePrice,
        PaymentMethod paymentMethod,
        CarResponse car,
        CustomerResponse customer,
        EmployeeResponse employee,
        BranchResponse branch
) {
}
