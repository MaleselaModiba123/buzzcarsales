package za.ac.cput.controller;

import za.ac.cput.domain.Branch;
import za.ac.cput.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @PostMapping("/create")
    public ResponseEntity<Branch> create(@Valid @RequestBody Branch branch) {
        return ResponseEntity.ok(branchService.save(branch));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Branch> read(@PathVariable Integer id) {
        return branchService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Branch>> getAll(Pageable pageable) {
        return ResponseEntity.ok(branchService.getAll(pageable));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Branch> update(@PathVariable Integer id, @Valid @RequestBody Branch branch) {
        return ResponseEntity.ok(branchService.update(id, branch));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        branchService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
