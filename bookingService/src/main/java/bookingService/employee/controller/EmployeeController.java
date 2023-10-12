package bookingService.employee.controller;

import bookingService.employee.dto.EmployeeDto;
import bookingService.employee.model.Employee;
import bookingService.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Employee create(@Valid @RequestBody EmployeeDto employeeDto, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return employeeService.createEmployee(employeeDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable int employeeId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        employeeService.removeEmployee(employeeId);
    }

    @PatchMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable int employeeId,
                                   @Valid @RequestBody Employee updatedEmployee,
                                   HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return employeeService.updateEmployee(employeeId, updatedEmployee);
    }

    @PatchMapping("/onshift/{employeeId}")
    public Employee updateEmployee(@PathVariable int employeeId,
                                   HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return employeeService.updateEmployee(employeeId);
    }

    @GetMapping("/{productId}")
    public Employee getEmployeeById(@PathVariable int employeeId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return employeeService.getEmployeeById(employeeId);
    }

    @GetMapping
    public List<Employee> getAllEmployee(HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return employeeService.getAllEmployee();
    }

}