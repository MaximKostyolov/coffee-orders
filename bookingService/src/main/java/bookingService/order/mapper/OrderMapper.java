package bookingService.order.mapper;

import bookingService.order.dto.NewOrderDto;
import dto.OrderEvent;

import java.time.LocalDateTime;

public class OrderMapper {

    public static OrderEvent orderDtoToOrderEvent(NewOrderDto orderDto) {
        return OrderEvent.builder()
                    .customerId(orderDto.getCustomerId())
                    .productId(orderDto.getProductId())
                    .timeStamp(LocalDateTime.now())
                    .status("REGISTERED")
                    .build();
    }

}