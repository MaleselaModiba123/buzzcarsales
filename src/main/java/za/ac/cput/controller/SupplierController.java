package za.ac.cput.controller;

import za.ac.cput.dto.request.SupplierRequest;
import za.ac.cput.dto.response.SupplierResponse;
import za.ac.cput.mapper.SupplierMapper;
import za.ac.cput.service.SupplierService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Suppliers", description = "Vehicle suppliers")
@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;

    @PostMapping("/create")
    public ResponseEntity<SupplierResponse> create(@Valid @RequestBody SupplierRequest request) {
        return ResponseEntity.ok(supplierMapper.toResponse(supplierService.save(supplierMapper.toEntity(request))));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<SupplierResponse> read(@PathVariable Integer id) {
        return supplierService.getById(id)
                .map(supplierMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<SupplierResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(supplierService.getAll(pageable).map(supplierMapper::toResponse));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SupplierResponse> update(@PathVariable Integer id, @Valid @RequestBody SupplierRequest request) {
        return ResponseEntity.ok(supplierMapper.toResponse(supplierService.update(id, supplierMapper.toEntity(request))));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
