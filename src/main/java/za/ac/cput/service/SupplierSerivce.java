package za.ac.cput.service;

import com.buzzcar.sales.entity.Supplier;
import com.buzzcar.sales.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Page<Supplier> getAll(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }

    public Optional<Supplier> getById(Integer id) {
        return supplierRepository.findById(id);
    }

    public Supplier update(Integer id, Supplier updated) {
        return supplierRepository.findById(id).map(existing -> {
            existing.setSupplierName(updated.getSupplierName());
            existing.setStreetAddress(updated.getStreetAddress());
            existing.setCity(updated.getCity());
            existing.setProvince(updated.getProvince());
            existing.setPostalCode(updated.getPostalCode());
            existing.setPhoneNumber(updated.getPhoneNumber());
            existing.setEmail(updated.getEmail());
            return supplierRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
    }

    public void delete(Integer id) {
        supplierRepository.deleteById(id);
    }
}
