package eventsStore.service;

import dto.Order;
import dto.OrderEvent;

public interface OrderService {

    void publishEvent(OrderEvent event);

    Order findOrder(int id);

    OrderEvent findLastOrderEvent(int orderId);

}