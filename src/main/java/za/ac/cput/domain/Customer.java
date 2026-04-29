import com.buzzcar.sales.enums.Province;
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

    @Enumerated(EnumType.STRING)
    private Province province;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Sale> sales;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Sms> smsList;
}