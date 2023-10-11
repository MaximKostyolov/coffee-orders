package bookingService.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import bookingService.product.model.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Integer> {

}