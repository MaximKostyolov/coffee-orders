package bookingService.customer.repository;

import bookingService.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<Customer, Integer> {

}
