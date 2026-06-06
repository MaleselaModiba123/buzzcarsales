package za.ac.cput.domain;
import za.ac.cput.enums.Province;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "supplier")
public class Supplier{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierId;

    @NotBlank(message = "supplierName is required")
    private String supplierName;

    private String contactPerson;
    private String phoneNumber;

    @Email(message = "email must be a valid address")
    private String email;

    private String streetAddress;
    private String city;

    @Enumerated(EnumType.STRING)
    private Province province;

    private String postalCode;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Car> cars;
}
