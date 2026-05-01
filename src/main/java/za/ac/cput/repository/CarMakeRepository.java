package za.ac.cput.repository;

import za.ac.cput.domain.CarMake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarMakeRepository extends JpaRepository<CarMake, Integer> {
}