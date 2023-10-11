package bookingService.product.service;

import bookingService.exception.ConflictException;
import bookingService.exception.NotFoundException;
import bookingService.product.dto.ProductDto;
import bookingService.product.mapper.ProductMapper;
import bookingService.product.model.Product;
import bookingService.product.repository.ProductJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductJpaRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        try {
            return productRepository.save(ProductMapper.productDtoToProduct(productDto));
        } catch (RuntimeException exception) {
            throw new ConflictException("Product with name: " + productDto.getName() + " is already exist.");
        }
    }

    @Override
    public void removeProduct(int productId) {
        if (productRepository.findById(productId).isPresent()) {
            productRepository.deleteById(productId);
        } else {
            throw new NotFoundException("Product with id = " + productId + " not found");
        }
    }

    @Override
    public Product updateProduct(int productId, Product updatedProduct) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new NotFoundException("Product with id = " + productId + " not found"));
        if (product.getName().equals(updatedProduct.getName())) {
            productRepository.deleteById(productId);
            return productRepository.save(updatedProduct);
        } else {
            try {
                return productRepository.save(updatedProduct);
            } catch (RuntimeException exception) {
                throw new ConflictException("Product with name: " + updatedProduct.getName() + " is already exist.");
            }
        }
    }

    @Override
    public Product getProductById(int productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product with id = " +
                productId + " not found"));
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

}