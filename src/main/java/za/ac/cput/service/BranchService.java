package za.ac.cput.service;

import za.ac.cput.domain.Branch;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BranchService {
    private final BranchRepository branchRepository;

    @Transactional
    public Branch save(Branch branch) {
        return branchRepository.save(branch);
    }

    public Branch read(Integer id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", id));
    }

    public Page<Branch> getAll(Pageable pageable) {
        return branchRepository.findAll(pageable);
    }

    public Optional<Branch> getById(Integer id) {
        return branchRepository.findById(id);
    }

    @Transactional
    public Branch update(Integer id, Branch updated) {
        return branchRepository.findById(id).map(existing -> {
            existing.setBranchName(updated.getBranchName());
            existing.setStreetAddress(updated.getStreetAddress());
            existing.setCity(updated.getCity());
            existing.setProvince(updated.getProvince());
            existing.setPostalCode(updated.getPostalCode());
            existing.setPhoneNumber(updated.getPhoneNumber());
            existing.setEmail(updated.getEmail());
            return branchRepository.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("Branch", id));
    }

    @Transactional
    public void delete(Integer id) {
        if (!branchRepository.existsById(id)) {
            throw new ResourceNotFoundException("Branch", id);
        }
        branchRepository.deleteById(id);
    }
}
