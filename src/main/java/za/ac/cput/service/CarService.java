package za.ac.cput.service;

import com.buzzcar.sales.entity.Car;
import com.buzzcar.sales.enums.CarStatus;
import com.buzzcar.sales.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    
    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car read(Integer id) {
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
    }
    @Override
    public Page<Car> getAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    @Override
    public Optional<Car> getById(Integer id) {
        return carRepository.findById(id);
    }

    @Override
    public List<Car> getByStatus(CarStatus status) {
        return carRepository.findByStatus(status);
    }

    @Override
    public List<Car> getByBranch(Integer branchId) {
        return carRepository.findByBranch_BranchId(branchId);
    }

    @Override
    public Car update(Integer id, Car updated) {
        return carRepository.findById(id).map(existing -> {
            existing.setVin(updated.getVin());
            existing.setYear(updated.getYear());
            existing.setColor(updated.getColor());
            existing.setMileage(updated.getMileage());
            existing.setStatus(updated.getStatus());
            existing.setCarModel(updated.getCarModel());
            existing.setBranch(updated.getBranch());
            return carRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
    }

    @Override
    public void delete(Integer id) {
        carRepository.deleteById(id);
    }
}
