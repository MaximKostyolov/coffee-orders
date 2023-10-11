package bookingService.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @NotBlank
    @Size(min = 2, max = 250)
    private String name;

    @NotBlank
    @Size(min = 2, max = 50)
    private String position;

    @NotBlank
    @Pattern(regexp = "^\\d{11}$")
    private String phoneNumber;

    private Boolean onShift;

}