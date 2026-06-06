package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "car_make")
public class CarMake{
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer makeId;

    @NotBlank(message = "makeName is required")
    private String makeName;

    private String originCountry;

    @JsonIgnore
    @OneToMany(mappedBy = "carMake", cascade = CascadeType.ALL)
    private List<CarModel> carModels;
}
