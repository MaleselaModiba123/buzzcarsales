package za.ac.cput.mapper;

import za.ac.cput.domain.Branch;
import za.ac.cput.domain.Car;
import za.ac.cput.domain.CarModel;
import za.ac.cput.domain.Supplier;
import za.ac.cput.dto.request.CarRequest;
import za.ac.cput.dto.response.CarResponse;
import za.ac.cput.exception.ResourceNotFoundException;
import za.ac.cput.repository.BranchRepository;
import za.ac.cput.repository.CarModelRepository;
import za.ac.cput.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarMapper {

    private final CarModelRepository carModelRepository;
    private final BranchRepository branchRepository;
    private final SupplierRepository supplierRepository;
    private final CarModelMapper carModelMapper;
    private final BranchMapper branchMapper;
    private final SupplierMapper supplierMapper;

    public Car toEntity(CarRequest r) {
        Car c = new Car();
        c.setVinNumber(r.vinNumber());
        c.setMileage(r.mileage());
        c.setColor(r.color());
        c.setYear(r.year());
        c.setPrice(r.price());
        c.setCondition(r.condition());
        c.setStatus(r.status());
        c.setCarModel(findModel(r.modelId()));
        c.setBranch(findBranch(r.branchId()));
        c.setSupplier(findSupplier(r.supplierId()));
        return c;
    }

    public CarResponse toResponse(Car c) {
        if (c == null) {
            return null;
        }
        return new CarResponse(
                c.getCarId(), c.getVinNumber(), c.getMileage(), c.getColor(), c.getYear(),
                c.getPrice(), c.getCondition(), c.getStatus(),
                carModelMapper.toResponse(c.getCarModel()),
                branchMapper.toResponse(c.getBranch()),
                supplierMapper.toResponse(c.getSupplier()));
    }

    private CarModel findModel(Integer id) {
        return carModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CarModel", id));
    }

    private Branch findBranch(Integer id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch", id));
    }

    private Supplier findSupplier(Integer id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
    }
}
