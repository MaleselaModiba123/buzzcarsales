package za.ac.cput.mapper;

import za.ac.cput.domain.Customer;
import za.ac.cput.domain.Sale;
import za.ac.cput.domain.Sms;
import za.ac.cput.dto.request.SmsRequest;
import za.ac.cput.dto.response.SmsResponse;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.CustomerRepository;
import za.ac.cput.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsMapper {

    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;

    public Sms toEntity(SmsRequest r) {
        Sms sms = new Sms();
        sms.setPhoneNumber(r.phoneNumber());
        sms.setMessage(r.message());
        sms.setSmsType(r.smsType());
        sms.setStatus(r.status());
        sms.setCustomer(findCustomer(r.customerId()));
        if (r.saleId() != null) {
            sms.setSale(findSale(r.saleId()));
        }
        return sms;
    }

    public SmsResponse toResponse(Sms sms) {
        if (sms == null) {
            return null;
        }
        Customer customer = sms.getCustomer();
        Sale sale = sms.getSale();
        return new SmsResponse(
                sms.getSmsId(), sms.getPhoneNumber(), sms.getMessage(), sms.getSmsType(),
                sms.getSentAt(), sms.getStatus(),
                customer != null ? customer.getCustomerId() : null,
                sale != null ? sale.getSaleId() : null);
    }

    private Customer findCustomer(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
    }

    private Sale findSale(Integer id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", id));
    }
}
