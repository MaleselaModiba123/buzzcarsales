package za.ac.cput.service;


import za.ac.cput.domain.Car;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.CarRepository;
import za.ac.cput.enums.*;
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
public class CarService {
    private final CarRepository carRepository;


    @Transactional
    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Car read(Integer id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));
    }

    public Page<Car> getAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    public Optional<Car> getById(Integer id) {
        return carRepository.findById(id);
    }

    public List<Car> getByStatus(Status status) {
        return carRepository.findByStatus(status);
    }

    public List<Car> getByBranch(Integer branchId) {
        return carRepository.findByBranch_BranchId(branchId);
    }

    @Transactional
    public Car update(Integer id, Car updated) {
        return carRepository.findById(id).map(existing -> {
            existing.setVinNumber(updated.getVinNumber());
            existing.setMileage(updated.getMileage());
            existing.setColor(updated.getColor());
            existing.setYear(updated.getYear());
            existing.setPrice(updated.getPrice());
            existing.setCondition(updated.getCondition());
            existing.setStatus(updated.getStatus());
            existing.setCarModel(updated.getCarModel());
            existing.setBranch(updated.getBranch());
            existing.setSupplier(updated.getSupplier());
            return carRepository.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("Car", id));
    }

    @Transactional
    public void delete(Integer id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException("Car", id);
        }
        carRepository.deleteById(id);
    }
}
