package za.ac.cput.service;

import com.buzzcar.sales.entity.Employee;
import com.buzzcar.sales.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee read(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

     @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Page<Employee> getAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Optional<Employee> getById(Integer id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> getByBranch(Integer branchId) {
        return employeeRepository.findByBranch_BranchId(branchId);
    }

    @Override
    public Employee update(Integer id, Employee updated) {
        return employeeRepository.findById(id).map(existing -> {
            existing.setFirstName(updated.getFirstName());
            existing.setLastName(updated.getLastName());
            existing.setStreetAddress(updated.getStreetAddress());
            existing.setCity(updated.getCity());
            existing.setProvince(updated.getProvince());
            existing.setPostalCode(updated.getPostalCode());
            existing.setPhoneNumber(updated.getPhoneNumber());
            existing.setEmail(updated.getEmail());
            return employeeRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    @Override
    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }
}
