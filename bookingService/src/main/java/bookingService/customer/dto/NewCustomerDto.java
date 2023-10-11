package bookingService.customer.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCustomerDto {

    @NotBlank
    @Size(min = 2, max = 250)
    private String name;

    @NotBlank
    @Pattern(regexp = "^\\d{11}$")
    private String phoneNumber;

    @Email
    private String email;

}