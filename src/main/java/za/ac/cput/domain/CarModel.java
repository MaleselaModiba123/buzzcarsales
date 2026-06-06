package za.ac.cput.domain;

import za.ac.cput.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "car_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer modelId;

    @NotNull(message = "carMake is required")
    @ManyToOne
    @JoinColumn(name = "make_id", nullable = false)
    private CarMake carMake;

    @NotBlank(message = "modelName is required")
    private String modelName;

    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    private Gearbox gearbox;

    private Integer numDoors;

    @JsonIgnore
    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL)
    private List<Car> cars;
}
