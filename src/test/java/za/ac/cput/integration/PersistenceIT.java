package za.ac.cput.integration;

import za.ac.cput.domain.*;
import za.ac.cput.enums.*;
import za.ac.cput.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test against a REAL MySQL database (profile "integration").
 *
 * <p>Unlike the H2-backed unit/slice tests, this verifies the schema actually
 * builds on MySQL — catching reserved-word column issues (e.g. {@code year},
 * {@code condition}) — and that every custom derived-query finder works against
 * the real engine. Runs only under the {@code it} Maven profile, which provides
 * a MySQL instance (a service container in CI, or a local server).
 */
@SpringBootTest
@ActiveProfiles("integration")
@Transactional
class PersistenceIT {

    @Autowired private CarMakeRepository carMakeRepository;
    @Autowired private CarModelRepository carModelRepository;
    @Autowired private BranchRepository branchRepository;
    @Autowired private SupplierRepository supplierRepository;
    @Autowired private CarRepository carRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private SaleRepository saleRepository;
    @Autowired private SmsRepository smsRepository;

    @Test
    void fullGraphPersistsAndDerivedQueriesWork() {
        // --- makes / models ---
        CarMake make = new CarMake();
        make.setMakeName("Toyota");
        make.setOriginCountry("Japan");
        make = carMakeRepository.save(make);

        CarModel model = new CarModel();
        model.setCarMake(make);
        model.setModelName("Corolla");
        model.setBodyType(BodyType.SEDAN);
        model.setFuelType(FuelType.PETROL);
        model.setGearbox(Gearbox.MANUAL);
        model.setNumDoors(4);
        model = carModelRepository.save(model);

        // --- branch / supplier ---
        Branch branch = new Branch();
        branch.setBranchName("Cape Town CBD");
        branch.setCity("Cape Town");
        branch.setProvince(Province.WESTERN_CAPE);
        branch = branchRepository.save(branch);

        Supplier supplier = new Supplier();
        supplier.setSupplierName("AutoWholesale");
        supplier = supplierRepository.save(supplier);

        // --- car: exercises the reserved-word columns (year -> manufacture_year,
        //     condition -> car_condition) on real MySQL ---
        Car car = new Car();
        car.setCarModel(model);
        car.setBranch(branch);
        car.setSupplier(supplier);
        car.setVinNumber("VIN-IT-1");
        car.setMileage(15000);
        car.setColor("White");
        car.setYear(2022);
        car.setPrice(350000.0);
        car.setCondition(Condition.USED);
        car.setStatus(Status.AVAILABLE);
        car = carRepository.save(car);

        assertThat(carRepository.findByStatus(Status.AVAILABLE))
                .extracting(Car::getVinNumber).contains("VIN-IT-1");
        assertThat(carRepository.findByBranch_BranchId(branch.getBranchId()))
                .hasSize(1);

        // --- customer + findByIdNumber ---
        Customer customer = new Customer();
        customer.setFirstName("Thabo");
        customer.setLastName("Mokoena");
        customer.setIdNumber("9001015800087");
        customer.setPhoneNumber("0820000000");
        customer.setProvince(Province.WESTERN_CAPE);
        customer = customerRepository.save(customer);

        assertThat(customerRepository.findByIdNumber("9001015800087")).isPresent();

        // --- employee + findByBranch ---
        Employee employee = new Employee();
        employee.setBranch(branch);
        employee.setFirstName("Sara");
        employee.setLastName("Naidoo");
        employee.setIdNumber("8505123456789");
        employee.setJobTitle(JobTitle.SALES_AGENT);
        employee.setHireDate(LocalDate.of(2024, 1, 15));
        employee = employeeRepository.save(employee);

        assertThat(employeeRepository.findByBranch_BranchId(branch.getBranchId()))
                .hasSize(1);

        // --- sale + its derived finders ---
        Sale sale = new Sale();
        sale.setCar(car);
        sale.setCustomer(customer);
        sale.setEmployee(employee);
        sale.setBranch(branch);
        sale.setSaleDate(LocalDate.now());
        sale.setSalePrice(350000.0);
        sale.setPaymentMethod(PaymentMethod.CASH);
        sale = saleRepository.save(sale);

        assertThat(saleRepository.findByCustomer_CustomerId(customer.getCustomerId())).hasSize(1);
        assertThat(saleRepository.findByBranch_BranchId(branch.getBranchId())).hasSize(1);
        assertThat(saleRepository.findByEmployee_EmployeeId(employee.getEmployeeId())).hasSize(1);

        // --- sms log + its derived finders ---
        Sms sms = new Sms();
        sms.setCustomer(customer);
        sms.setSale(sale);
        sms.setPhoneNumber("0820000000");
        sms.setMessage("Confirmation");
        sms.setSentAt(LocalDateTime.now());
        sms.setStatus(SmsStatus.SENT);
        smsRepository.save(sms);

        assertThat(smsRepository.findByCustomer_CustomerId(customer.getCustomerId())).hasSize(1);
        assertThat(smsRepository.findBySale_SaleId(sale.getSaleId())).hasSize(1);
    }
}
