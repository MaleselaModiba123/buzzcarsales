package za.ac.cput.controller;

import za.ac.cput.domain.Sale;
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

    @PostMapping("/create")
    public ResponseEntity<Sale> create(@Valid @RequestBody Sale sale) {
        return ResponseEntity.ok(saleService.save(sale));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Sale> read(@PathVariable Integer id) {
        return saleService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Sale>> getAll(Pageable pageable) {
        return ResponseEntity.ok(saleService.getAll(pageable));
    }

    @GetMapping("/getByCustomerId/{customerId}")
    public ResponseEntity<List<Sale>> getByCustomerId(@PathVariable Integer customerId) {
        return ResponseEntity.ok(saleService.getByCustomerId(customerId));
    }

    @GetMapping("/getByBranchId/{branchId}")
    public ResponseEntity<List<Sale>> getByBranchId(@PathVariable Integer branchId) {
        return ResponseEntity.ok(saleService.getByBranch(branchId));
    }

    @GetMapping("/getByEmployeeId/{employeeId}")
    public ResponseEntity<List<Sale>> getByEmployeeId(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(saleService.getByEmployeeId(employeeId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Sale> update(@PathVariable Integer id, @Valid @RequestBody Sale sale) {
        return ResponseEntity.ok(saleService.update(id, sale));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
