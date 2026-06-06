package za.ac.cput.service;

import za.ac.cput.domain.Supplier;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplierService {
    private final SupplierRepository supplierRepository;

    @Transactional
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Page<Supplier> getAll(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }

    public Optional<Supplier> getById(Integer id) {
        return supplierRepository.findById(id);
    }

    @Transactional
    public Supplier update(Integer id, Supplier updated) {
        return supplierRepository.findById(id).map(existing -> {
            existing.setSupplierName(updated.getSupplierName());
            existing.setContactPerson(updated.getContactPerson());
            existing.setPhoneNumber(updated.getPhoneNumber());
            existing.setEmail(updated.getEmail());
            existing.setStreetAddress(updated.getStreetAddress());
            existing.setCity(updated.getCity());
            existing.setProvince(updated.getProvince());
            existing.setPostalCode(updated.getPostalCode());
            return supplierRepository.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
    }

    @Transactional
    public void delete(Integer id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Supplier", id);
        }
        supplierRepository.deleteById(id);
    }
}
