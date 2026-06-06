package za.ac.cput.controller;

import za.ac.cput.dto.request.CarRequest;
import za.ac.cput.dto.response.CarResponse;
import za.ac.cput.mapper.CarMapper;
import za.ac.cput.service.CarService;
import jakarta.validation.Valid;
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
    private final CarMapper carMapper;

    @PostMapping("/create")
    public ResponseEntity<CarResponse> create(@Valid @RequestBody CarRequest request) {
        return ResponseEntity.ok(carMapper.toResponse(carService.save(carMapper.toEntity(request))));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CarResponse> read(@PathVariable Integer id) {
        return carService.getById(id)
                .map(carMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<CarResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(carService.getAll(pageable).map(carMapper::toResponse));
    }

    @GetMapping("/getByBranchId/{branchId}")
    public ResponseEntity<List<CarResponse>> getByBranchId(@PathVariable Integer branchId) {
        return ResponseEntity.ok(carService.getByBranch(branchId).stream().map(carMapper::toResponse).toList());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CarResponse> update(@PathVariable Integer id, @Valid @RequestBody CarRequest request) {
        return ResponseEntity.ok(carMapper.toResponse(carService.update(id, carMapper.toEntity(request))));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
