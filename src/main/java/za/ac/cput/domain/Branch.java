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
@Table(name = "branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer branchId;

    @NotBlank(message = "branchName is required")
    private String branchName;

    private String streetAddress;
    private String city;

    @Enumerated(EnumType.STRING)
    private Province province;

    private String postalCode;
    private String phoneNumber;

    @Email(message = "email must be a valid address")
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @JsonIgnore
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Car> cars;

    @JsonIgnore
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Sale> sales;


}
