package bookingService.customer.service;

import bookingService.customer.dto.NewCustomerDto;
import bookingService.customer.mapper.CustomerMapper;
import bookingService.customer.model.Customer;
import bookingService.customer.repository.CustomerJpaRepository;
import bookingService.exception.ConflictException;
import bookingService.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerJpaRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerJpaRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(NewCustomerDto customerDto) {
        try {
            return customerRepository.save(CustomerMapper.customerDtoToСustomer(customerDto));
        } catch (RuntimeException exception) {
            throw new ConflictException("Customer with phone: " + customerDto.getPhoneNumber() + " is already exist.");
        }
    }

    @Override
    public void removeCustomer(int customerId) {
        if (customerRepository.findById(customerId).isPresent()) {
            customerRepository.deleteById(customerId);
        } else {
            throw new NotFoundException("Customer with id = " + customerId + " not found");
        }
    }

    @Override
    public Customer updateCustomer(int customerId, Customer updatedCustomer) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new NotFoundException("Customer with id = " + customerId + " not found"));
        if (updatedCustomer.getPhoneNumber().equals(customer.getPhoneNumber())) {
            return customerRepository.save(updatedCustomer);
        } else {
            try {
                return customerRepository.save(updatedCustomer);
            } catch (RuntimeException exception) {
                throw new ConflictException("Customer with phone: " + updatedCustomer.getPhoneNumber() +
                        " is already exist.");
            }
        }
    }

    @Override
    public Customer getCustomerById(int customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer with id = " +
                customerId + " not found"));
    }

}