package za.ac.cput.domain;
import za.ac.cput.enums.JobTitle;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "employee")
public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String idNumber;

    private String phoneNumber;
    private String email;

    @Enumerated(EnumType.STRING)
    private JobTitle jobTitle;

    private LocalDate hireDate;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Sale> sales;

}