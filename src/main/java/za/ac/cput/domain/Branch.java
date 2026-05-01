package za.ac.cput.domain;

import za.ac.cput.enums.Province;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer branchId;

    private String branchName;
    private String streetAddress;
    private String city;

    @Enumerated(EnumType.STRING)
    private Province province;

    private String postalCode;
    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Car> cars;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Sale> sales;


}