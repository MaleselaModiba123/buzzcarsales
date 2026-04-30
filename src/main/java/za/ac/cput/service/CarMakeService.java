package za.ac.cput.service;

import com.buzzcar.sales.entity.CarMake;
import com.buzzcar.sales.repository.CarMakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CarMakeService {
    private final CarMakeRepository carMakeRepository;

    @Autowired
    public CarMakeService(CarMakeRepository carMakeRepository) {
        this.carMakeRepository = carMakeRepository;
    }

    @Override
    public CarMake save(CarMake carMake) {
        return carMakeRepository.save(carMake);
    }

    @Override
    public CarMake read(Integer id) {
        return carMakeRepository.findById(id).orElseThrow(() -> new RuntimeException("CarMake not found with id: " + id));
    }

    @Override
    public Page<CarMake> getAll(Pageable pageable) {
        return carMakeRepository.findAll(pageable);
    }

    @Override
    public Optional<CarMake> getById(Integer id) {
        return carMakeRepository.findById(id);
    }

    @Override
    public CarMake update(Integer id, CarMake updated) {
        return carMakeRepository.findById(id).map(existing -> {
            existing.setMakeName(updated.getMakeName());
            existing.setOriginCountry(updated.getOriginCountry());
            return carMakeRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("CarMake not found with id: " + id));
    }

    @Override
    public void delete(Integer id) {
        carMakeRepository.deleteById(id);
    }
}
