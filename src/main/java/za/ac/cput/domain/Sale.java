package za.ac.cput.domain;

import za.ac.cput.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "sale")
public class Sale{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer saleId;

    @NotNull(message = "car is required")
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @NotNull(message = "customer is required")
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull(message = "employee is required")
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @NotNull(message = "branch is required")
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(name = "date")
    private LocalDate saleDate;

    @PositiveOrZero(message = "salePrice cannot be negative")
    private Double salePrice;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
}
