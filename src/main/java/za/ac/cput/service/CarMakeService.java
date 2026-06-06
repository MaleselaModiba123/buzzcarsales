package za.ac.cput.service;


import za.ac.cput.domain.CarMake;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.CarMakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarMakeService {
    private final CarMakeRepository carMakeRepository;

    @Transactional
    public CarMake save(CarMake carMake) {
        return carMakeRepository.save(carMake);
    }

    public CarMake read(Integer id) {
        return carMakeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CarMake", id));
    }

    public Page<CarMake> getAll(Pageable pageable) {
        return carMakeRepository.findAll(pageable);
    }

    public Optional<CarMake> getById(Integer id) {
        return carMakeRepository.findById(id);
    }

    @Transactional
    public CarMake update(Integer id, CarMake updated) {
        return carMakeRepository.findById(id).map(existing -> {
            existing.setMakeName(updated.getMakeName());
            existing.setOriginCountry(updated.getOriginCountry());
            return carMakeRepository.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("CarMake", id));
    }

    @Transactional
    public void delete(Integer id) {
        if (!carMakeRepository.existsById(id)) {
            throw new ResourceNotFoundException("CarMake", id);
        }
        carMakeRepository.deleteById(id);
    }
}
