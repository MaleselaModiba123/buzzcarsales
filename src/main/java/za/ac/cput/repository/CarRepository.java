import com.buzzcar.sales.entity.Car;
import com.buzzcar.sales.enums.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByStatus(CarStatus status);
    List<Car> findByBranch_BranchId(Integer branchId);
}