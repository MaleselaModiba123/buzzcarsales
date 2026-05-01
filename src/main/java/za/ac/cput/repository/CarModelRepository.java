package za.ac.cput.repository;

import za.ac.cput.domain.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Integer> {
    List<CarModel> findByCarMake_MakeId(Integer makeId);
}