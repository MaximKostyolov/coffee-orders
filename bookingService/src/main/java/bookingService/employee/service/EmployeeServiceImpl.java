package bookingService.employee.service;

import bookingService.employee.dto.EmployeeDto;
import bookingService.employee.mapper.EmployeeMapper;
import bookingService.employee.model.Employee;
import bookingService.employee.repository.EmployeeJpaRepository;
import bookingService.exception.ConflictException;
import bookingService.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeJpaRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeJpaRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(EmployeeDto employeeDto) {
        try {
            return employeeRepository.save(EmployeeMapper.employeeDtoToEmployee(employeeDto));
        } catch (RuntimeException exception) {
            throw new ConflictException("Employee with phone: " + employeeDto.getPhoneNumber() + " is already exist.");
        }
    }

    @Override
    public void removeEmployee(int employeeId) {
        if (employeeRepository.findById(employeeId).isPresent()) {
            employeeRepository.deleteById(employeeId);
        } else {
            throw new NotFoundException("Product with id = " + employeeId + " not found");
        }
    }

    @Override
    public Employee updateEmployee(int employeeId, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new NotFoundException("Employee with id = " + employeeId + " not found"));
        if (updatedEmployee.getPhoneNumber().equals(employee.getPhoneNumber())) {
            employeeRepository.deleteById(employeeId);
            return employeeRepository.save(updatedEmployee);
        } else {
            try {
                return employeeRepository.save(updatedEmployee);
            } catch (RuntimeException exception) {
                throw new ConflictException("Employee with phone: " + updatedEmployee.getPhoneNumber() +
                        " is already exist.");
            }
        }
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() ->
                new NotFoundException("Employee with id = " + employeeId + " not found"));
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getAvaibleEmployee() {
        Optional<Employee> employee = employeeRepository.findAvailableEmployee(LocalDateTime.now());
        if (employee.isPresent()) {
            return employee.get();
        } else {
            return employeeRepository.findFirstEmployeeToAvailable();
        }
    }

}