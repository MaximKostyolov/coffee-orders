package bookingService.employee.service;

import bookingService.employee.dto.EmployeeDto;
import bookingService.employee.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(EmployeeDto employeeDto);

    void removeEmployee(int employeeId);

    Employee updateEmployee(int employeeId, Employee updatedEmployee);

    Employee getEmployeeById(int employeeId);

    List<Employee> getAllEmployee();

    Employee getAvaibleEmployee();

    Employee updateEmployee(int employeeId);

}