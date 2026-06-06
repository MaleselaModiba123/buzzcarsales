package za.ac.cput.mapper;

import za.ac.cput.domain.CarMake;
import za.ac.cput.domain.CarModel;
import za.ac.cput.dto.request.CarModelRequest;
import za.ac.cput.dto.response.CarModelResponse;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.CarMakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarModelMapper {

    private final CarMakeRepository carMakeRepository;
    private final CarMakeMapper carMakeMapper;

    public CarModel toEntity(CarModelRequest r) {
        CarModel m = new CarModel();
        m.setModelName(r.modelName());
        m.setBodyType(r.bodyType());
        m.setFuelType(r.fuelType());
        m.setGearbox(r.gearbox());
        m.setNumDoors(r.numDoors());
        m.setCarMake(findMake(r.makeId()));
        return m;
    }

    public CarModelResponse toResponse(CarModel m) {
        if (m == null) {
            return null;
        }
        return new CarModelResponse(
                m.getModelId(), m.getModelName(), m.getBodyType(), m.getFuelType(),
                m.getGearbox(), m.getNumDoors(), carMakeMapper.toResponse(m.getCarMake()));
    }

    private CarMake findMake(Integer makeId) {
        return carMakeRepository.findById(makeId)
                .orElseThrow(() -> new ResourceNotFoundException("CarMake", makeId));
    }
}
