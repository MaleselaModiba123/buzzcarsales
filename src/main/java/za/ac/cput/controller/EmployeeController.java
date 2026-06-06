package za.ac.cput.controller;

import za.ac.cput.dto.request.EmployeeRequest;
import za.ac.cput.dto.response.EmployeeResponse;
import za.ac.cput.mapper.EmployeeMapper;
import za.ac.cput.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @PostMapping("/create")
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeMapper.toResponse(employeeService.save(employeeMapper.toEntity(request))));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<EmployeeResponse> read(@PathVariable Integer id) {
        return employeeService.getById(id)
                .map(employeeMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<EmployeeResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(employeeService.getAll(pageable).map(employeeMapper::toResponse));
    }

    @GetMapping("/getByBranchId/{branchId}")
    public ResponseEntity<List<EmployeeResponse>> getByBranchId(@PathVariable Integer branchId) {
        return ResponseEntity.ok(employeeService.getByBranch(branchId).stream().map(employeeMapper::toResponse).toList());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Integer id, @Valid @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeMapper.toResponse(employeeService.update(id, employeeMapper.toEntity(request))));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
