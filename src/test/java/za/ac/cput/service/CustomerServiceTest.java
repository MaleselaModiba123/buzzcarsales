package za.ac.cput.service;

import za.ac.cput.domain.Customer;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer(Integer id, String idNumber) {
        Customer c = new Customer();
        c.setCustomerId(id);
        c.setFirstName("Thabo");
        c.setLastName("Mokoena");
        c.setIdNumber(idNumber);
        return c;
    }

    @Test
    void getByIdNumber_returnsCustomer_whenFound() {
        when(customerRepository.findByIdNumber("9001015800087"))
                .thenReturn(Optional.of(customer(1, "9001015800087")));

        assertThat(customerService.getByIdNumber("9001015800087").getCustomerId()).isEqualTo(1);
    }

    @Test
    void getByIdNumber_throwsNotFound_whenMissing() {
        when(customerRepository.findByIdNumber("0000")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getByIdNumber("0000"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("0000");
    }

    @Test
    void update_copiesIdNumber() {
        Customer existing = customer(1, "OLD");
        Customer updated = customer(null, "NEW");
        when(customerRepository.findById(1)).thenReturn(Optional.of(existing));
        when(customerRepository.save(existing)).thenReturn(existing);

        Customer result = customerService.update(1, updated);

        assertThat(result.getIdNumber()).isEqualTo("NEW");
    }

    @Test
    void delete_throwsNotFound_whenMissing() {
        when(customerRepository.existsById(7)).thenReturn(false);

        assertThatThrownBy(() -> customerService.delete(7))
                .isInstanceOf(ResourceNotFoundException.class);
        verify(customerRepository, never()).deleteById(7);
    }
}
