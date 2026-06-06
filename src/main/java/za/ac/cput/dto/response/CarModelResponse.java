package za.ac.cput.dto.response;

import za.ac.cput.enums.BodyType;
import za.ac.cput.enums.FuelType;
import za.ac.cput.enums.Gearbox;

public record CarModelResponse(
        Integer modelId,
        String modelName,
        BodyType bodyType,
        FuelType fuelType,
        Gearbox gearbox,
        Integer numDoors,
        CarMakeResponse carMake
) {
}
