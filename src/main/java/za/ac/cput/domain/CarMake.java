
package za.ac.cput.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "car_make")
public class CarMake{
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer makeId;

    private String makeName;
    private String originCountry;

    @OneToMany(mappedBy = "carMake", cascade = CascadeType.ALL)
    private List<CarModel> carModels;

    public Object getMakeName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMakeName'");
    }
}