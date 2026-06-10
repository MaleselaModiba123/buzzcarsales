package za.ac.cput.mapper;

import za.ac.cput.domain.Customer;
import za.ac.cput.domain.Sale;
import za.ac.cput.domain.Sms;
import za.ac.cput.dto.request.SmsRequest;
import za.ac.cput.dto.response.SmsResponse;
import za.ac.cput.enums.SmsStatus;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.CustomerRepository;
import za.ac.cput.repository.SaleRepository;
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
class SmsMapperTest {

    @Mock private CustomerRepository customerRepository;
    @Mock private SaleRepository saleRepository;

    @InjectMocks
    private SmsMapper smsMapper;

    @Test
    void toEntity_withoutSaleId_doesNotTouchSaleRepository() {
        Customer customer = new Customer();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        SmsRequest req = new SmsRequest(1, null, "082", "hello", "INFO", SmsStatus.SENT);

        Sms sms = smsMapper.toEntity(req);

        assertThat(sms.getCustomer()).isSameAs(customer);
        assertThat(sms.getSale()).isNull();
        assertThat(sms.getMessage()).isEqualTo("hello");
        verify(saleRepository, never()).findById(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void toEntity_withSaleId_resolvesSale() {
        Customer customer = new Customer();
        Sale sale = new Sale();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(saleRepository.findById(9)).thenReturn(Optional.of(sale));
        SmsRequest req = new SmsRequest(1, 9, "082", "hi", null, SmsStatus.SENT);

        Sms sms = smsMapper.toEntity(req);

        assertThat(sms.getSale()).isSameAs(sale);
    }

    @Test
    void toEntity_throwsNotFound_whenCustomerMissing() {
        when(customerRepository.findById(404)).thenReturn(Optional.empty());
        SmsRequest req = new SmsRequest(404, null, "082", "x", null, SmsStatus.SENT);

        assertThatThrownBy(() -> smsMapper.toEntity(req))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Customer");
    }

    @Test
    void toResponse_flattensCustomerAndSaleIds() {
        Customer customer = new Customer();
        customer.setCustomerId(11);
        Sale sale = new Sale();
        sale.setSaleId(22);
        Sms sms = new Sms();
        sms.setSmsId(3);
        sms.setPhoneNumber("082");
        sms.setStatus(SmsStatus.DELIVERED);
        sms.setCustomer(customer);
        sms.setSale(sale);

        SmsResponse response = smsMapper.toResponse(sms);

        assertThat(response.smsId()).isEqualTo(3);
        assertThat(response.customerId()).isEqualTo(11);
        assertThat(response.saleId()).isEqualTo(22);
        assertThat(response.status()).isEqualTo(SmsStatus.DELIVERED);
    }

    @Test
    void toResponse_handlesNullSale() {
        Sms sms = new Sms();
        sms.setSmsId(1);
        sms.setCustomer(new Customer());

        SmsResponse response = smsMapper.toResponse(sms);

        assertThat(response.saleId()).isNull();
    }
}
