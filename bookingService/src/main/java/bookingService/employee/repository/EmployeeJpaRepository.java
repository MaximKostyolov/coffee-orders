package bookingService.employee.repository;

import bookingService.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Integer> {

    @Query(value = "select * from employees e " +
            "where (e.on_shift = 'true') " +
            "and (DATE(e.time_to_available) < ?1) " +
            "order by e.count_order ASC " +
            "limit 1", nativeQuery = true)
    Optional<Employee> findAvailableEmployee(LocalDateTime now);

    @Query(value = "select * from employees e " +
            "where (e.on_shift = 'true') " +
            "order by e.time_to_available ASC " +
            "limit 1", nativeQuery = true)
    Employee findFirstEmployeeToAvailable();

}