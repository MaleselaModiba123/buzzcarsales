package za.ac.cput.domain;

import za.ac.cput.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "sale")
public class Sale{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer saleId;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(name = "date")
    private LocalDate saleDate;

    private Double salePrice;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    // @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    // private List<Sms> smsList;
}