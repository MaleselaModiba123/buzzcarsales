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
@Table(name = "customer")
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    private String phoneNumber;

    @Email(message = "email must be a valid address")
    private String email;

    private String streetAddress;
    private String city;

    @NotBlank(message = "idNumber is required")
    @Column(unique = true, nullable = false)
    private String idNumber;

    @Enumerated(EnumType.STRING)
    private Province province;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Sale> sales;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Sms> smsList;
}
