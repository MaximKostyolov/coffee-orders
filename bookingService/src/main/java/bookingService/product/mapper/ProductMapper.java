package bookingService.product.mapper;

import bookingService.product.dto.ProductDto;
import bookingService.product.model.Product;

public class ProductMapper {

    public static Product productDtoToProduct(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .cookingTime(productDto.getCookingTime())
                .build();
    }

}