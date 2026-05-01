package za.ac.cput.service;

import za.ac.cput.domain.Sms;
import za.ac.cput.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SmsService {
    private static final SmsRepository smsRepository = null;

    public static Sms save(Sms sms) {
        return smsRepository.save(sms);
    }

    public Page<Sms> getAll(Pageable pageable) {
        return smsRepository.findAll(pageable);
    }

    public Optional<Sms> getById(Integer id) {
        return smsRepository.findById(id);
    }

    public List<Sms> getByCustomerId(Integer customerId) {
        return smsRepository.findByCustomer_CustomerId(customerId);
    }

    public List<Sms> getBySale(Integer saleId) {
        return smsRepository.findBySale_SaleId(saleId);
    }
    public void delete(Integer id) {
        smsRepository.deleteById(id);
    }
}
