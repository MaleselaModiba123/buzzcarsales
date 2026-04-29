import com.buzzcar.sales.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    List<Sale> findByCustomer_CustomerId(Integer customerId);
    List<Sale> findByBranch_BranchId(Integer branchId);
    List<Sale> findByEmployee_EmployeeId(Integer employeeId);
}