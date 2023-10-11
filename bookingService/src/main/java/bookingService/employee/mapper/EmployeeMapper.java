package bookingService.employee.mapper;

import bookingService.employee.dto.EmployeeDto;
import bookingService.employee.model.Employee;

public class EmployeeMapper {

    public static Employee employeeDtoToEmployee(EmployeeDto employeeDto) {
        Employee employee = Employee.builder()
                                    .name(employeeDto.getName())
                                    .position(employeeDto.getPosition())
                                    .phoneNumber(employeeDto.getPhoneNumber())
                                    .countOrder(0)
                                    .build();
        if (employeeDto.getOnShift() == null) {
            employee.setOnShift(false);
        } else {
            employee.setOnShift(employeeDto.getOnShift());
        }
        return employee;
    }

}