package za.ac.cput.controller;

import za.ac.cput.dto.request.SaleRequest;
import za.ac.cput.dto.response.SaleResponse;
import za.ac.cput.mapper.SaleMapper;
import za.ac.cput.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;
    private final SaleMapper saleMapper;

    @PostMapping("/create")
    public ResponseEntity<SaleResponse> create(@Valid @RequestBody SaleRequest request) {
        return ResponseEntity.ok(saleMapper.toResponse(saleService.save(saleMapper.toEntity(request))));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<SaleResponse> read(@PathVariable Integer id) {
        return saleService.getById(id)
                .map(saleMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<SaleResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(saleService.getAll(pageable).map(saleMapper::toResponse));
    }

    @GetMapping("/getByCustomerId/{customerId}")
    public ResponseEntity<List<SaleResponse>> getByCustomerId(@PathVariable Integer customerId) {
        return ResponseEntity.ok(saleService.getByCustomerId(customerId).stream().map(saleMapper::toResponse).toList());
    }

    @GetMapping("/getByBranchId/{branchId}")
    public ResponseEntity<List<SaleResponse>> getByBranchId(@PathVariable Integer branchId) {
        return ResponseEntity.ok(saleService.getByBranch(branchId).stream().map(saleMapper::toResponse).toList());
    }

    @GetMapping("/getByEmployeeId/{employeeId}")
    public ResponseEntity<List<SaleResponse>> getByEmployeeId(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(saleService.getByEmployeeId(employeeId).stream().map(saleMapper::toResponse).toList());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SaleResponse> update(@PathVariable Integer id, @Valid @RequestBody SaleRequest request) {
        return ResponseEntity.ok(saleMapper.toResponse(saleService.update(id, saleMapper.toEntity(request))));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
