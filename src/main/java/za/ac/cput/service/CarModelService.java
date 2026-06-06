package za.ac.cput.service;

import za.ac.cput.domain.CarModel;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.CarModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarModelService {
    private final CarModelRepository carModelRepository;

    @Transactional
    public CarModel save(CarModel carModel) {
        return carModelRepository.save(carModel);
    }

    public CarModel read(Integer id) {
        return carModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CarModel", id));
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

    @Transactional
    public CarModel update(Integer id, CarModel updated) {
        return carModelRepository.findById(id).map(existing -> {
            existing.setModelName(updated.getModelName());
            existing.setCarMake(updated.getCarMake());
            existing.setBodyType(updated.getBodyType());
            existing.setFuelType(updated.getFuelType());
            existing.setGearbox(updated.getGearbox());
            existing.setNumDoors(updated.getNumDoors());
            return carModelRepository.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("CarModel", id));
    }

    @Transactional
    public void delete(Integer id) {
        if (!carModelRepository.existsById(id)) {
            throw new ResourceNotFoundException("CarModel", id);
        }
        carModelRepository.deleteById(id);
    }
}
