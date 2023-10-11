package bookingService.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Integer id;

    @NotBlank
    @Size(min = 2, max = 250)
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Integer cookingTime;

}