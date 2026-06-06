package za.ac.cput.mapper;

import za.ac.cput.domain.Branch;
import za.ac.cput.domain.Car;
import za.ac.cput.domain.Customer;
import za.ac.cput.domain.Employee;
import za.ac.cput.domain.Sale;
import za.ac.cput.dto.request.SaleRequest;
import za.ac.cput.dto.response.SaleResponse;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.BranchRepository;
import za.ac.cput.repository.CarRepository;
import za.ac.cput.repository.CustomerRepository;
import za.ac.cput.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleMapper {

    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final CarMapper carMapper;
    private final CustomerMapper customerMapper;
    private final EmployeeMapper employeeMapper;
    private final BranchMapper branchMapper;

    public Sale toEntity(SaleRequest r) {
        Sale s = new Sale();
        s.setSaleDate(r.saleDate());
        s.setSalePrice(r.salePrice());
        s.setPaymentMethod(r.paymentMethod());
        s.setCar(findCar(r.carId()));
        s.setCustomer(findCustomer(r.customerId()));
        s.setEmployee(findEmployee(r.employeeId()));
        s.setBranch(findBranch(r.branchId()));
        return s;
    }

    public SaleResponse toResponse(Sale s) {
        if (s == null) {
            return null;
        }
        return new SaleResponse(
                s.getSaleId(), s.getSaleDate(), s.getSalePrice(), s.getPaymentMethod(),
                carMapper.toResponse(s.getCar()),
                customerMapper.toResponse(s.getCustomer()),
                employeeMapper.toResponse(s.getEmployee()),
                branchMapper.toResponse(s.getBranch()));
    }

    private Car findCar(Integer id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", id));
    }

    private Customer findCustomer(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
    }

    private Employee findEmployee(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
    }

    private Branch findBranch(Integer id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", id));
    }
}
