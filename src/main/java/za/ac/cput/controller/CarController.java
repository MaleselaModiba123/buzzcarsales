package za.ac.cput.controller;

import com.buzzcar.sales.entity.Car;
import com.buzzcar.sales.enums.CarStatus;
import com.buzzcar.sales.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("/create")
    public ResponseEntity<Car> create(@RequestBody Car car) {
        return ResponseEntity.ok(carService.save(car));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Car> read(@PathVariable Integer id) {
        return carService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Car>> getAll(Pageable pageable) {
        return ResponseEntity.ok(carService.getAll(pageable));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Car> getById(@PathVariable Integer id) {
        return carService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<List<Car>> getByStatus(@PathVariable CarStatus status) {
        return ResponseEntity.ok(carService.getByStatus(status));
    }

    @GetMapping("/getByBranchId/{branchId}")
    public ResponseEntity<List<Car>> getByBranchId(@PathVariable Integer branchId) {
        return ResponseEntity.ok(carService.getByBranchId(branchId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Car> update(@PathVariable Integer id, @RequestBody Car car) {
        return ResponseEntity.ok(carService.update(id, car));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
