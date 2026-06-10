package za.ac.cput.service;

import za.ac.cput.domain.Car;
import za.ac.cput.enums.Condition;
import za.ac.cput.enums.Status;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private Car car(Integer id) {
        Car c = new Car();
        c.setCarId(id);
        c.setVinNumber("VIN-" + id);
        c.setColor("White");
        c.setYear(2022);
        c.setPrice(300000.0);
        c.setCondition(Condition.USED);
        c.setStatus(Status.AVAILABLE);
        return c;
    }

    @Test
    void save_delegatesToRepository() {
        Car c = car(1);
        when(carRepository.save(c)).thenReturn(c);

        assertThat(carService.save(c)).isSameAs(c);
        verify(carRepository).save(c);
    }

    @Test
    void read_returnsCar_whenFound() {
        when(carRepository.findById(1)).thenReturn(Optional.of(car(1)));

        assertThat(carService.read(1).getCarId()).isEqualTo(1);
    }

    @Test
    void read_throwsNotFound_whenMissing() {
        when(carRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> carService.read(99))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Car")
                .hasMessageContaining("99");
    }

    @Test
    void getByStatus_delegates() {
        when(carRepository.findByStatus(Status.SOLD)).thenReturn(List.of(car(1)));

        assertThat(carService.getByStatus(Status.SOLD)).hasSize(1);
    }

    @Test
    void update_copiesFields_whenFound() {
        Car existing = car(1);
        Car updated = new Car();
        updated.setVinNumber("NEW-VIN");
        updated.setColor("Black");
        updated.setStatus(Status.SOLD);
        updated.setMileage(50000);
        when(carRepository.findById(1)).thenReturn(Optional.of(existing));
        when(carRepository.save(any(Car.class))).thenAnswer(inv -> inv.getArgument(0));

        Car result = carService.update(1, updated);

        assertThat(result.getVinNumber()).isEqualTo("NEW-VIN");
        assertThat(result.getColor()).isEqualTo("Black");
        assertThat(result.getStatus()).isEqualTo(Status.SOLD);
        assertThat(result.getMileage()).isEqualTo(50000);
    }

    @Test
    void update_throwsNotFound_whenMissing() {
        when(carRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> carService.update(99, new Car()))
                .isInstanceOf(ResourceNotFoundException.class);
        verify(carRepository, never()).save(any());
    }

    @Test
    void delete_removes_whenExists() {
        when(carRepository.existsById(1)).thenReturn(true);

        carService.delete(1);

        verify(carRepository).deleteById(1);
    }

    @Test
    void delete_throwsNotFound_whenMissing() {
        when(carRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> carService.delete(99))
                .isInstanceOf(ResourceNotFoundException.class);
        verify(carRepository, never()).deleteById(any());
    }
}
