package za.ac.cput.service;

import za.ac.cput.domain.CarModel;
import za.ac.cput.repository.CarModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarModelService {
    private final CarModelRepository carModelRepository;

    public CarModel save(CarModel carModel) {
        return carModelRepository.save(carModel);
    }

    public CarModel read(Integer id) {
        return carModelRepository.findById(id).orElseThrow(() -> new RuntimeException("CarModel not found with id: " + id));
    }

    public Page<CarModel> getAll(Pageable pageable) {
        return carModelRepository.findAll(pageable);
    }

    public Optional<CarModel> getById(Integer id) {
        return carModelRepository.findById(id);
    }

    public List<CarModel> getByMakeId(Integer makeId) {
    return carModelRepository.findByCarMake_MakeId(makeId);
}

    public CarModel update(Integer id, CarModel updated) {
    return carModelRepository.findById(id).map(existing -> {
        existing.setModelName(updated.getModelName());
        existing.setCarMake(updated.getCarMake());
        existing.setBodyType(updated.getBodyType());
        existing.setFuelType(updated.getFuelType());
        existing.setGearbox(updated.getGearbox());
        existing.setNumDoors(updated.getNumDoors());
        return carModelRepository.save(existing);
    }).orElseThrow(() -> new RuntimeException("CarModel not found with id: " + id));
}
    public void delete(Integer id) {
        carModelRepository.deleteById(id);
    }
}
