package za.ac.cput.dto.request;

import za.ac.cput.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

public record SaleRequest(
        @NotNull(message = "carId is required") Integer carId,
        @NotNull(message = "customerId is required") Integer customerId,
        @NotNull(message = "employeeId is required") Integer employeeId,
        @NotNull(message = "branchId is required") Integer branchId,
        LocalDate saleDate,
        @PositiveOrZero(message = "salePrice cannot be negative") Double salePrice,
        PaymentMethod paymentMethod
) {
}
