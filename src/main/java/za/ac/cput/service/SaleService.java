package za.ac.cput.service;

import com.buzzcar.sales.entity.Sale;
import com.buzzcar.sales.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    @Autowired
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public Sale read(Integer id) {
        return saleRepository.findById(id).orElseThrow(() -> new RuntimeException("Sale not found with id: " + id));
    }

    @Override
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public Page<Sale> getAll(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }

    @Override
    public Optional<Sale> getById(Integer id) {
        return saleRepository.findById(id);
    }

    @Override
    public List<Sale> getByCustomerId(Integer customerId) {
        return saleRepository.findByCustomer_CustomerId(customerId);
    }

    @Override
    public List<Sale> getByBranch (Integer branchId) {
        return saleRepository.findByBranch_BranchId(branchId);
    }

    @Override
    public List<Sale> getByEmployeeId(Integer employeeId) {
        return saleRepository.findByEmployee_EmployeeId(employeeId);
    }

    @Override
    public Sale update(Integer id, Sale updated) {
        return saleRepository.findById(id).map(existing -> {
            existing.setSaleDate(updated.getSaleDate());
            existing.setSalePrice(updated.getSalePrice());
            existing.setCustomer(updated.getCustomer());
            existing.setEmployee(updated.getEmployee());
            existing.setBranch(updated.getBranch());
            existing.setCar(updated.getCar());
            return saleRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Sale not found with id: " + id));
    }

    @Override
    public void delete(Integer id) {
        saleRepository.deleteById(id);
    }
}
