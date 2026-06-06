package za.ac.cput.mapper;

import za.ac.cput.domain.Customer;
import za.ac.cput.dto.request.CustomerRequest;
import za.ac.cput.dto.response.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequest r) {
        Customer c = new Customer();
        c.setFirstName(r.firstName());
        c.setLastName(r.lastName());
        c.setPhoneNumber(r.phoneNumber());
        c.setEmail(r.email());
        c.setStreetAddress(r.streetAddress());
        c.setCity(r.city());
        c.setIdNumber(r.idNumber());
        c.setProvince(r.province());
        return c;
    }

    public CustomerResponse toResponse(Customer c) {
        if (c == null) {
            return null;
        }
        return new CustomerResponse(
                c.getCustomerId(), c.getFirstName(), c.getLastName(), c.getPhoneNumber(),
                c.getEmail(), c.getStreetAddress(), c.getCity(), c.getIdNumber(), c.getProvince());
    }
}
