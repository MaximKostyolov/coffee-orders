package bookingService.customer.mapper;

import bookingService.customer.dto.NewCustomerDto;
import bookingService.customer.model.Customer;

public class CustomerMapper {

    public static Customer customerDtoToСustomer(NewCustomerDto customerDto) {
        return Customer.builder()
                .name(customerDto.getName())
                .phoneNumber(customerDto.getPhoneNumber())
                .email(customerDto.getEmail())
                .build();
    }

}