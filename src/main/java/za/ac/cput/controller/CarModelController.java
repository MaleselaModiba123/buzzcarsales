package za.ac.cput.controller;

import za.ac.cput.dto.request.CarModelRequest;
import za.ac.cput.dto.response.CarModelResponse;
import za.ac.cput.mapper.CarModelMapper;
import za.ac.cput.service.CarModelService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Car Models", description = "Vehicle models per make")
@RestController
@RequestMapping("/carmodels")
@RequiredArgsConstructor
public class CarModelController {
    private final CarModelService carModelService;
    private final CarModelMapper carModelMapper;

    @PostMapping("/create")
    public ResponseEntity<CarModelResponse> create(@Valid @RequestBody CarModelRequest request) {
        return ResponseEntity.ok(carModelMapper.toResponse(carModelService.save(carModelMapper.toEntity(request))));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CarModelResponse> read(@PathVariable Integer id) {
        return carModelService.getById(id)
                .map(carModelMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<CarModelResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(carModelService.getAll(pageable).map(carModelMapper::toResponse));
    }

    @GetMapping("/getByMakeId/{makeId}")
    public ResponseEntity<List<CarModelResponse>> getByMakeId(@PathVariable Integer makeId) {
        return ResponseEntity.ok(carModelService.getByMakeId(makeId).stream().map(carModelMapper::toResponse).toList());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CarModelResponse> update(@PathVariable Integer id, @Valid @RequestBody CarModelRequest request) {
        return ResponseEntity.ok(carModelMapper.toResponse(carModelService.update(id, carModelMapper.toEntity(request))));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        carModelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
