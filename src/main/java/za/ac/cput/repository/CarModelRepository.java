import com.buzzcar.sales.entity.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Integer> {
    List<CarModel> findByCarMakeMakeName(String makeName);
}