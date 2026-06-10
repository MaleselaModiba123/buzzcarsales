package za.ac.cput.controller;

import za.ac.cput.dto.request.BranchRequest;
import za.ac.cput.dto.response.BranchResponse;
import za.ac.cput.mapper.BranchMapper;
import za.ac.cput.service.BranchService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Branches", description = "Dealership branches")
@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;
    private final BranchMapper branchMapper;

    @PostMapping("/create")
    public ResponseEntity<BranchResponse> create(@Valid @RequestBody BranchRequest request) {
        return ResponseEntity.ok(branchMapper.toResponse(branchService.save(branchMapper.toEntity(request))));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BranchResponse> read(@PathVariable Integer id) {
        return branchService.getById(id)
                .map(branchMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<BranchResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(branchService.getAll(pageable).map(branchMapper::toResponse));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BranchResponse> update(@PathVariable Integer id, @Valid @RequestBody BranchRequest request) {
        return ResponseEntity.ok(branchMapper.toResponse(branchService.update(id, branchMapper.toEntity(request))));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        branchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
