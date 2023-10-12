package bookingService.customer.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 250)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Pattern(regexp = "^\\d{11}$") // номер телефона, начиная с 7 или 8 без пробелов и прочих символов
    @Column(name = "phone")
    private String phoneNumber;

    @Email
    @Column(name = "e_mail")
    private String email;

}