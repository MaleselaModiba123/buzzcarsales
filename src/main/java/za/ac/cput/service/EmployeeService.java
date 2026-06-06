package za.ac.cput.service;

import za.ac.cput.domain.Employee;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.EmployeeRepository;
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
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee read(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
    }

    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Page<Employee> getAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Optional<Employee> getById(Integer id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getByBranch(Integer branchId) {
        return employeeRepository.findByBranch_BranchId(branchId);
    }

    @Transactional
    public Employee update(Integer id, Employee updated) {
        return employeeRepository.findById(id).map(existing -> {
            existing.setFirstName(updated.getFirstName());
            existing.setLastName(updated.getLastName());
            existing.setPhoneNumber(updated.getPhoneNumber());
            existing.setEmail(updated.getEmail());
            return employeeRepository.save(existing);
        }).orElseThrow(() -> new ResourceNotFoundException("Employee", id));
    }

    @Transactional
    public void delete(Integer id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee", id);
        }
        employeeRepository.deleteById(id);
    }
}
