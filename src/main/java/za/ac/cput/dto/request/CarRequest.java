package za.ac.cput.dto.request;

import za.ac.cput.enums.Condition;
import za.ac.cput.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CarRequest(
        @NotNull(message = "modelId is required") Integer modelId,
        @NotNull(message = "branchId is required") Integer branchId,
        @NotNull(message = "supplierId is required") Integer supplierId,
        @NotBlank(message = "vinNumber is required") String vinNumber,
        @PositiveOrZero(message = "mileage cannot be negative") Integer mileage,
        String color,
        Integer year,
        @PositiveOrZero(message = "price cannot be negative") Double price,
        Condition condition,
        Status status
) {
}
