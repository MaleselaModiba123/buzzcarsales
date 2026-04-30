package za.ac.cput.service;

import com.buzzcar.sales.entity.Customer;
import com.buzzcar.sales.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

     @Override  
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer read(Integer id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @Override
    public Page<Customer> getAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Optional<Customer> getById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer update(Integer id, Customer updated) {
        return customerRepository.findById(id).map(existing -> {
            existing.setFirstName(updated.getFirstName());
            existing.setLastName(updated.getLastName());
            existing.setStreetAddress(updated.getStreetAddress());
            existing.setCity(updated.getCity());
            existing.setProvince(updated.getProvince());
            existing.setPostalCode(updated.getPostalCode());
            existing.setPhoneNumber(updated.getPhoneNumber());
            existing.setEmail(updated.getEmail());
            return customerRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @Override
    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }
    
}
