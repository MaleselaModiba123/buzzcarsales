package za.ac.cput.controller;

import za.ac.cput.domain.*;
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

    @PostMapping("/create")
    public ResponseEntity<Employee> create(@Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.save(employee));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Employee> read(@PathVariable Integer id) {
        return employeeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Employee>> getAll(Pageable pageable) {
        return ResponseEntity.ok(employeeService.getAll(pageable));
    }

    @GetMapping("/getByBranchId/{branchId}")
    public ResponseEntity<List<Employee>> getByBranchId(@PathVariable Integer branchId) {
        return ResponseEntity.ok(employeeService.getByBranch(branchId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> update(@PathVariable Integer id, @Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.update(id, employee));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
