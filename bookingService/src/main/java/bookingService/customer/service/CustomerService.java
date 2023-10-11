package bookingService.customer.service;

import bookingService.customer.dto.NewCustomerDto;
import bookingService.customer.model.Customer;

public interface CustomerService {
    Customer createCustomer(NewCustomerDto customerDto);

    void removeCustomer(int customerId);

    Customer updateCustomer(int customerId, Customer updatedCustomer);

    Customer getCustomerById(int customerId);

}