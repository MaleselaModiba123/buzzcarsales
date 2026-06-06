package za.ac.cput.dto.response;

import za.ac.cput.enums.Condition;
import za.ac.cput.enums.Status;

public record CarResponse(
        Integer carId,
        String vinNumber,
        Integer mileage,
        String color,
        Integer year,
        Double price,
        Condition condition,
        Status status,
        CarModelResponse carModel,
        BranchResponse branch,
        SupplierResponse supplier
) {
}
