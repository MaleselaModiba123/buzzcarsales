package za.ac.cput.service;

import za.ac.cput.domain.Branch;
import za.ac.cput.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;

    public Branch save(Branch branch) {
        return branchRepository.save(branch);
    }

    public Branch read(Integer id) {
        return branchRepository.findById(id).orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
    }

    public Page<Branch> getAll(Pageable pageable) {
        return branchRepository.findAll(pageable);
    }

    public Optional<Branch> getById(Integer id) {
        return branchRepository.findById(id);
    }

    public Branch update(Integer id, Branch updated) {
        return (Branch) branchRepository.findById(id).map(existing -> {
            existing.setBranchName(updated.getBranchName());
            existing.setStreetAddress(updated.getStreetAddress());
            existing.setCity(updated.getCity());
            existing.setProvince(updated.getProvince());
            existing.setPostalCode(updated.getPostalCode());
            existing.setPhoneNumber(updated.getPhoneNumber());
            existing.setEmail(updated.getEmail());
            return branchRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
    }

    public void delete(Integer id) {
        branchRepository.deleteById(id);
    }
}
