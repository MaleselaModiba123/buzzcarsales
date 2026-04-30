package za.ac.cput.repository;

import com.buzzcar.sales.entity.Sms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SmsRepository extends JpaRepository<Sms, Integer> {
    List<Sms> findByCustomer_CustomerId(Integer customerId);
    List<Sms> findBySale_SaleId(Integer saleId);
}