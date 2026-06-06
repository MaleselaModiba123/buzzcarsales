package za.ac.cput.mapper;

import za.ac.cput.domain.CarMake;
import za.ac.cput.dto.request.CarMakeRequest;
import za.ac.cput.dto.response.CarMakeResponse;
import org.springframework.stereotype.Component;

@Component
public class CarMakeMapper {

    public CarMake toEntity(CarMakeRequest r) {
        CarMake m = new CarMake();
        m.setMakeName(r.makeName());
        m.setOriginCountry(r.originCountry());
        return m;
    }

    public CarMakeResponse toResponse(CarMake m) {
        if (m == null) {
            return null;
        }
        return new CarMakeResponse(m.getMakeId(), m.getMakeName(), m.getOriginCountry());
    }
}
