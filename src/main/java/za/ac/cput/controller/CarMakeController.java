package za.ac.cput.controller;

import za.ac.cput.dto.request.CarMakeRequest;
import za.ac.cput.dto.response.CarMakeResponse;
import za.ac.cput.mapper.CarMakeMapper;
import za.ac.cput.service.CarMakeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carmakes")
public class CarMakeController {
    private final CarMakeService carMakeService;
    private final CarMakeMapper carMakeMapper;

    @PostMapping("/create")
    public ResponseEntity<CarMakeResponse> create(@Valid @RequestBody CarMakeRequest request) {
        return ResponseEntity.ok(carMakeMapper.toResponse(carMakeService.save(carMakeMapper.toEntity(request))));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CarMakeResponse> read(@PathVariable Integer id) {
        return carMakeService.getById(id)
                .map(carMakeMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<CarMakeResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(carMakeService.getAll(pageable).map(carMakeMapper::toResponse));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CarMakeResponse> update(@PathVariable Integer id, @Valid @RequestBody CarMakeRequest request) {
        return ResponseEntity.ok(carMakeMapper.toResponse(carMakeService.update(id, carMakeMapper.toEntity(request))));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        carMakeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
