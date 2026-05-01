package za.ac.cput.repository;

import za.ac.cput.domain.Car;
import za.ac.cput.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByBranch_BranchId(Integer branchId);
    List<Car> findByStatus(Status status);
}