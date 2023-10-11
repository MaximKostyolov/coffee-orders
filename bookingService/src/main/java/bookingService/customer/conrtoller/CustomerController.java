package bookingService.customer.conrtoller;

import bookingService.customer.dto.NewCustomerDto;
import bookingService.customer.model.Customer;
import bookingService.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Customer create(@Valid @RequestBody NewCustomerDto customerDto, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return customerService.createCustomer(customerDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable int customerId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        customerService.removeCustomer(customerId);
    }

    @PatchMapping("/{customerId}")
    public Customer updateCustomer(@PathVariable int customerId,
                                   @Valid @RequestBody Customer updatedCustomer,
                                   HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return customerService.updateCustomer(customerId, updatedCustomer);
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable int customerId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return customerService.getCustomerById(customerId);
    }

}