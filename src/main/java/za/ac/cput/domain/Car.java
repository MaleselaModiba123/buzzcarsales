import com.buzzcar.sales.enums.*;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "car")
public class Car{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "make_id", nullable = false)
    private CarMake carMake;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    private String color;
    private Integer year;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Sale> sales;
}