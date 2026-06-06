package za.ac.cput.service;

import za.ac.cput.domain.*;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaleService {
    private final SaleRepository saleRepository;
    private final SmsSenderService smsSenderService;


    public Sale read(Integer id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", id));
    }

    @Transactional
    public Sale save(Sale sale) {
        Sale saved = saleRepository.save(sale);
        // Fire-and-forget: SMS delivery runs asynchronously so it can never
        // block the request or roll back the persisted sale.
        smsSenderService.sendSaleConfirmation(saved);
        return saved;
    }

    public Page<Sale> getAll(Pageable pageable) {
        return saleRepository.findAll(pageable);
    }

    public Optional<Sale> getById(Integer id) {
        return saleRepository.findById(id);
    }

    public List<Sale> getByCustomerId(Integer customerId) {
        return saleRepository.findByCustomer_CustomerId(customerId);
    }

    public List<Sale> getByBranch (Integer branchId) {
        return saleRepository.findByBranch_BranchId(branchId);
    }

    public List<Sale> getByEmployeeId(Integer employeeId) {
        return saleRepository.findByEmployee_EmployeeId(employeeId);
    }

    @Transactional
    public Sale update(Integer id, Sale updated) {
        return saleRepository.findById(id).map(existing -> {
            existing.setSaleDate(updated.getSaleDate());
            existing.setSalePrice(updated.getSalePrice());
            existing.setCustomer(updated.getCustomer());
            existing.setEmployee(updated.getEmployee());
            existing.setBranch(updated.getBranch());
            existing.setCar(updated.getCar());
            return saleRepository.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("Sale", id));
    }

    @Transactional
    public void delete(Integer id) {
        if (!saleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sale", id);
        }
        saleRepository.deleteById(id);
    }
}
