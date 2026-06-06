package za.ac.cput.controller;

import za.ac.cput.dto.request.CustomerRequest;
import za.ac.cput.dto.response.CustomerResponse;
import za.ac.cput.mapper.CustomerMapper;
import za.ac.cput.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerMapper.toResponse(customerService.save(customerMapper.toEntity(request))));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<CustomerResponse> read(@PathVariable Integer id) {
        return customerService.getById(id)
                .map(customerMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<CustomerResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(customerService.getAll(pageable).map(customerMapper::toResponse));
    }

    @GetMapping("/getByIdNumber/{idNumber}")
    public ResponseEntity<CustomerResponse> getByIdNumber(@PathVariable String idNumber) {
        return ResponseEntity.ok(customerMapper.toResponse(customerService.getByIdNumber(idNumber)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Integer id, @Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerMapper.toResponse(customerService.update(id, customerMapper.toEntity(request))));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
