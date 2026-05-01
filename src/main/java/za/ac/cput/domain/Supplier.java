
package za.ac.cput.domain;
import za.ac.cput.enums.Province;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "supplier")
public class Supplier{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierId;

    private String supplierName;
    private String contactPerson;
    private String phoneNumber;
    private String email;
    private String streetAddress;
    private String city;

    @Enumerated(EnumType.STRING)
    private Province province;

    private String postalCode;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Car> cars;
}