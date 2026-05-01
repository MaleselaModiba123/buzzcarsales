package za.ac.cput.service;


import za.ac.cput.domain.CarMake;
import za.ac.cput.repository.CarMakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarMakeService {
    private final CarMakeRepository carMakeRepository;

    public CarMake save(CarMake carMake) {
        return carMakeRepository.save(carMake);
    }

    public CarMake read(Integer id) {
        return carMakeRepository.findById(id).orElseThrow(() -> new RuntimeException("CarMake not found with id: " + id));
    }

    public Page<CarMake> getAll(Pageable pageable) {
        return carMakeRepository.findAll(pageable);
    }

    public Optional<CarMake> getById(Integer id) {
        return carMakeRepository.findById(id);
    }

    public CarMake update(Integer id, CarMake updated) {
        return carMakeRepository.findById(id).map(existing -> {
            existing.setMakeName((String) updated.getMakeName());
            existing.setOriginCountry(updated.getOriginCountry());
            return carMakeRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("CarMake not found with id: " + id));
    }

    public void delete(Integer id) {
        carMakeRepository.deleteById(id);
    }
}
