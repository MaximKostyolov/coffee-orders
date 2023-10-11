package bookingService.product.service;

import bookingService.product.dto.ProductDto;
import bookingService.product.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDto productDto);

    void removeProduct(int productId);

    Product updateProduct(int productId, Product updatedProduct);

    Product getProductById(int productId);

    List<Product> getAllProduct();

}