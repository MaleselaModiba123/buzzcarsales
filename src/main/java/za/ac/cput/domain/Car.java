package za.ac.cput.domain;

import za.ac.cput.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Entity
@Table(name = "car")
public class Car{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    @NotNull(message = "carModel is required")
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel carModel;


    @NotNull(message = "branch is required")
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @NotNull(message = "supplier is required")
    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @NotBlank(message = "vinNumber is required")
    @Column(unique = true, nullable = false)
    private String vinNumber;

    @PositiveOrZero(message = "mileage cannot be negative")
    private Integer mileage;

    private String color;

    // "year" is a reserved word in MySQL/H2; map to a safe column name.
    @Column(name = "manufacture_year")
    private Integer year;

    @PositiveOrZero(message = "price cannot be negative")
    private Double price;

    // "condition" is a reserved word in MySQL; map to a safe column name.
    @Column(name = "car_condition")
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Enumerated(EnumType.STRING)
    private Status status;
}
