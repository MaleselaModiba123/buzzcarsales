package za.ac.cput.dto.request;

import za.ac.cput.enums.BodyType;
import za.ac.cput.enums.FuelType;
import za.ac.cput.enums.Gearbox;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarModelRequest(
        @NotNull(message = "makeId is required") Integer makeId,
        @NotBlank(message = "modelName is required") String modelName,
        BodyType bodyType,
        FuelType fuelType,
        Gearbox gearbox,
        Integer numDoors
) {
}
