package za.ac.cput.mapper;

import za.ac.cput.domain.Branch;
import za.ac.cput.domain.Employee;
import za.ac.cput.dto.request.EmployeeRequest;
import za.ac.cput.dto.response.EmployeeResponse;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    public Employee toEntity(EmployeeRequest r) {
        Employee e = new Employee();
        e.setFirstName(r.firstName());
        e.setLastName(r.lastName());
        e.setIdNumber(r.idNumber());
        e.setPhoneNumber(r.phoneNumber());
        e.setEmail(r.email());
        e.setJobTitle(r.jobTitle());
        e.setHireDate(r.hireDate());
        e.setBranch(findBranch(r.branchId()));
        return e;
    }

    public EmployeeResponse toResponse(Employee e) {
        if (e == null) {
            return null;
        }
        return new EmployeeResponse(
                e.getEmployeeId(), e.getFirstName(), e.getLastName(), e.getIdNumber(),
                e.getPhoneNumber(), e.getEmail(), e.getJobTitle(), e.getHireDate(),
                branchMapper.toResponse(e.getBranch()));
    }

    private Branch findBranch(Integer id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", id));
    }
}
