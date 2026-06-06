package za.ac.cput.controller;

import za.ac.cput.domain.CarModel;
import za.ac.cput.service.CarModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/carmodels")
@RequiredArgsConstructor
public class CarModelController {
    private final CarModelService carModelService;

    @PostMapping("/create")
    public ResponseEntity<CarModel> create(@Valid @RequestBody CarModel carModel) {
        return ResponseEntity.ok(carModelService.save(carModel));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CarModel> read(@PathVariable Integer id) {
        return carModelService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<CarModel>> getAll(Pageable pageable) {
        return ResponseEntity.ok(carModelService.getAll(pageable));
    }

    @GetMapping("/getByMakeId/{makeId}")
    public ResponseEntity<List<CarModel>> getByMakeId(@PathVariable Integer makeId) {
        return ResponseEntity.ok(carModelService.getByMakeId(makeId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CarModel> update(@PathVariable Integer id, @Valid @RequestBody CarModel carModel) {
        return ResponseEntity.ok(carModelService.update(id, carModel));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        carModelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
