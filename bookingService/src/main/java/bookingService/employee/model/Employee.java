package bookingService.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 250)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "position", nullable = false)
    private String position;

    @NotBlank
    @Pattern(regexp = "^\\d{11}$")
    @Column(name = "phone", nullable = false)
    private String phoneNumber;

    @Column(name = "on_shift", columnDefinition = "boolean default true")
    private boolean onShift;

    @Column(name = "count_order")
    private Integer countOrder;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "time_to_available")
    private LocalDateTime timeToAvailable;

}