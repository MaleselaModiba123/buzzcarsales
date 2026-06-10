package za.ac.cput.mapper;

import za.ac.cput.domain.Branch;
import za.ac.cput.domain.Car;
import za.ac.cput.domain.CarModel;
import za.ac.cput.domain.Supplier;
import za.ac.cput.dto.request.CarRequest;
import za.ac.cput.dto.response.BranchResponse;
import za.ac.cput.dto.response.CarModelResponse;
import za.ac.cput.dto.response.CarResponse;
import za.ac.cput.dto.response.SupplierResponse;
import za.ac.cput.enums.Condition;
import za.ac.cput.enums.Status;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.BranchRepository;
import za.ac.cput.repository.CarModelRepository;
import za.ac.cput.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarMapperTest {

    @Mock private CarModelRepository carModelRepository;
    @Mock private BranchRepository branchRepository;
    @Mock private SupplierRepository supplierRepository;
    @Mock private CarModelMapper carModelMapper;
    @Mock private BranchMapper branchMapper;
    @Mock private SupplierMapper supplierMapper;

    @InjectMocks
    private CarMapper carMapper;

    private CarRequest request(Integer modelId) {
        return new CarRequest(modelId, 2, 3, "VIN-1", 1000, "Red", 2021, 250000.0,
                Condition.NEW, Status.AVAILABLE);
    }

    @Test
    void toEntity_resolvesForeignKeysAndScalarFields() {
        CarModel model = new CarModel();
        Branch branch = new Branch();
        Supplier supplier = new Supplier();
        when(carModelRepository.findById(1)).thenReturn(Optional.of(model));
        when(branchRepository.findById(2)).thenReturn(Optional.of(branch));
        when(supplierRepository.findById(3)).thenReturn(Optional.of(supplier));

        Car car = carMapper.toEntity(request(1));

        assertThat(car.getCarModel()).isSameAs(model);
        assertThat(car.getBranch()).isSameAs(branch);
        assertThat(car.getSupplier()).isSameAs(supplier);
        assertThat(car.getVinNumber()).isEqualTo("VIN-1");
        assertThat(car.getPrice()).isEqualTo(250000.0);
        assertThat(car.getCondition()).isEqualTo(Condition.NEW);
    }

    @Test
    void toEntity_throwsNotFound_whenModelMissing() {
        when(carModelRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> carMapper.toEntity(request(99)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("CarModel");
    }

    @Test
    void toResponse_buildsNestedDto() {
        Car car = new Car();
        car.setCarId(5);
        car.setVinNumber("VIN-5");
        car.setStatus(Status.SOLD);
        car.setCarModel(new CarModel());
        car.setBranch(new Branch());
        car.setSupplier(new Supplier());
        when(carModelMapper.toResponse(car.getCarModel()))
                .thenReturn(new CarModelResponse(1, "Corolla", null, null, null, 4, null));
        when(branchMapper.toResponse(car.getBranch()))
                .thenReturn(new BranchResponse(2, "CPT", null, null, null, null, null, null));
        when(supplierMapper.toResponse(car.getSupplier()))
                .thenReturn(new SupplierResponse(3, "AW", null, null, null, null, null, null, null));

        CarResponse response = carMapper.toResponse(car);

        assertThat(response.carId()).isEqualTo(5);
        assertThat(response.vinNumber()).isEqualTo("VIN-5");
        assertThat(response.status()).isEqualTo(Status.SOLD);
        assertThat(response.carModel().modelName()).isEqualTo("Corolla");
        assertThat(response.branch().branchName()).isEqualTo("CPT");
        assertThat(response.supplier().supplierName()).isEqualTo("AW");
    }

    @Test
    void toResponse_returnsNull_forNullEntity() {
        assertThat(carMapper.toResponse(null)).isNull();
    }
}
