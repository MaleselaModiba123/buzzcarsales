package za.ac.cput.controller;

import za.ac.cput.domain.Car;
import za.ac.cput.dto.request.CarRequest;
import za.ac.cput.dto.response.CarResponse;
import za.ac.cput.enums.Condition;
import za.ac.cput.enums.Status;
import za.ac.cput.mapper.CarMapper;
import za.ac.cput.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarService carService;

    @MockBean
    private CarMapper carMapper;

    private CarResponse response(Integer id) {
        return new CarResponse(id, "VIN-" + id, 0, "Red", 2020, 100.0,
                Condition.NEW, Status.AVAILABLE, null, null, null);
    }

    @Test
    void create_returns200_andBody_whenValid() throws Exception {
        CarRequest request = new CarRequest(1, 1, 1, "VIN-7", 1000, "Red", 2020, 100.0,
                Condition.NEW, Status.AVAILABLE);
        when(carMapper.toEntity(any())).thenReturn(new Car());
        when(carService.save(any())).thenReturn(new Car());
        when(carMapper.toResponse(any())).thenReturn(response(7));

        mockMvc.perform(post("/cars/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId").value(7))
                .andExpect(jsonPath("$.vinNumber").value("VIN-7"));
    }

    @Test
    void create_returns400_whenRequiredFieldsMissing() throws Exception {
        // empty body -> modelId/branchId/supplierId/vinNumber all fail @NotNull/@NotBlank
        mockMvc.perform(post("/cars/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.vinNumber").exists())
                .andExpect(jsonPath("$.errors.modelId").exists());
    }

    @Test
    void read_returns200_whenFound() throws Exception {
        Car car = new Car();
        when(carService.getById(1)).thenReturn(Optional.of(car));
        when(carMapper.toResponse(car)).thenReturn(response(1));

        mockMvc.perform(get("/cars/read/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId").value(1));
    }

    @Test
    void read_returns404_whenMissing() throws Exception {
        when(carService.getById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/cars/read/99"))
                .andExpect(status().isNotFound());
    }
}
