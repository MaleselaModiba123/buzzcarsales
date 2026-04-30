package za.ac.cput.service;

import com.buzzcar.sales.entity.Branch;
import com.buzzcar.sales.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public Branch save(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public Branch read(Integer id) {
        return branchRepository.findById(id).orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
    }

    @Override
    public Page<Branch> getAll(Pageable pageable) {
        return branchRepository.findAll(pageable);
    }

    @Override
    public Optional<Branch> getById(Integer id) {
        return branchRepository.findById(id);
    }

    @Override
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
        }).orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
    }

    @Override
    public void delete(Integer id) {
        branchRepository.deleteById(id);
    }
}
