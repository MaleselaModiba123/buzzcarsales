package za.ac.cput.service;

import com.buzzcar.sales.entity.CarModel;
import com.buzzcar.sales.repository.CarModelRepository;
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

    @Autowired
    public CarModelService(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    @Override
    public CarModel save(CarModel carModel) {
        return carModelRepository.save(carModel);
    }

    @Override
    public CarModel read(Integer id) {
        return carModelRepository.findById(id).orElseThrow(() -> new RuntimeException("CarModel not found with id: " + id));
    }

    @Override
    public Page<CarModel> getAll(Pageable pageable) {
        return carModelRepository.findAll(pageable);
    }

    @Override
    public Optional<CarModel> getById(Integer id) {
        return carModelRepository.findById(id);
    }

    @Override
    public List<CarModel> getByMakeId(Integer makeId) {
        return carModelRepository.findByCarMake_MakeId(makeId);
    }

    @Override
    public CarModel update(Integer id, CarModel updated) {
        return carModelRepository.findById(id).map(existing -> {
            existing.setModelName(updated.getModelName());
            existing.setBodyType(updated.getBodyType());
            existing.setFuelType(updated.getFuelType());
            existing.setTransmission(updated.getTransmission());
            existing.setNumDoors(updated.getNumDoors());
            existing.setCarMake(updated.getCarMake());
            return carModelRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("CarModel not found with id: " + id));
    }
    
    @Override
    public void delete(Integer id) {
        carModelRepository.deleteById(id);
    }
}
