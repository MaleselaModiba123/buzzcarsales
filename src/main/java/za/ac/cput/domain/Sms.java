
package za.ac.cput.domain;

import za.ac.cput.enums.*;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sms")
public class Sms{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer smsId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    private String phoneNumber;
    private String message;

    private String smsType;


    private LocalDateTime sentAt;

    @Enumerated(EnumType.STRING)
    private SmsStatus status;
}