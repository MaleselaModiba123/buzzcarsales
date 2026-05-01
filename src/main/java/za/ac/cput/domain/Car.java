package za.ac.cput.domain;

import za.ac.cput.enums.*;
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
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(unique = true, nullable = false)
    private String vinNumber;

    private Integer mileage;

    private String color;
    private Integer year;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Condition CarCondition;

    @Enumerated(EnumType.STRING)
    private Status status;

    // @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    // private List<Sale> sales;
}