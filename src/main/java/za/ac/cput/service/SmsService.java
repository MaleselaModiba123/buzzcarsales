package za.ac.cput.service;

import za.ac.cput.domain.Sms;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.SmsRepository;
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
public class SmsService {
    private final SmsRepository smsRepository;

    @Transactional
    public Sms save(Sms sms) {
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

    @Transactional
    public void delete(Integer id) {
        if (!smsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sms", id);
        }
        smsRepository.deleteById(id);
    }
}
