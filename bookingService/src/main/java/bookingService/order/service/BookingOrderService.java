package bookingService.order.service;

import bookingService.order.dto.NewOrderDto;
import dto.Order;

public interface BookingOrderService {

    void createOrder(NewOrderDto newOrderDto);

    void cancelOrder(int orderId);

    Order getOrderById(int orderId);

    void issueOrder(int orderId);

}