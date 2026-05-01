package za.ac.cput.domain;
import za.ac.cput.enums.Province;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String streetAddress;
    private String city;

    @Column(unique = true, nullable = false)
    private String idNumber;

    @Enumerated(EnumType.STRING)
    private Province province;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Sale> sales;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Sms> smsList;
}