package za.ac.cput.controller;

import za.ac.cput.domain.Sale;
import za.ac.cput.service.SaleService;
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
    public ResponseEntity<Sale> create(@RequestBody Sale sale) {
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

    @GetMapping("/getById/{id}")
    public ResponseEntity<Sale> getById(@PathVariable Integer id) {
        return saleService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
