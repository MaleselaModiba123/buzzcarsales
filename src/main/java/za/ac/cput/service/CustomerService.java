package za.ac.cput.service;

import za.ac.cput.domain.Customer;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer read(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
    }

    public Page<Customer> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Optional<Customer> getById(Integer id) {
        return customerRepository.findById(id);
    }

    @Transactional
    public Customer update(Integer id, Customer updated) {
        return customerRepository.findById(id).map(existing -> {
            existing.setFirstName(updated.getFirstName());
            existing.setLastName(updated.getLastName());
            existing.setStreetAddress(updated.getStreetAddress());
            existing.setCity(updated.getCity());
            existing.setProvince(updated.getProvince());
            existing.setPhoneNumber(updated.getPhoneNumber());
            existing.setEmail(updated.getEmail());
            return customerRepository.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer", id));
    }

    @Transactional
    public void delete(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer", id);
        }
        customerRepository.deleteById(id);
    }

    public Customer getByIdNumber(String idNumber) {
        return customerRepository.findByIdNumber(idNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with idNumber: " + idNumber));
    }

}
