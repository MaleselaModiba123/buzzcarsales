package za.ac.cput.domain;

import za.ac.cput.enums.*;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "car_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer modelId;

    @ManyToOne
    @JoinColumn(name = "make_id", nullable = false)
    private CarMake carMake;

    private String modelName;

    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    private Gearbox gearbox;

    private Integer numDoors;

    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL)
    private List<Car> cars;
}