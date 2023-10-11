package eventsStore.mapper;

import dto.EventView;
import dto.Order;
import dto.OrderEvent;
import eventsStore.model.Event;
import eventsStore.model.OrderEventView;

import java.util.ArrayList;
import java.util.List;

public class EventMapper {
    public static Event orderEventToEvent(OrderEvent event) {
        return Event.builder()
                .orderId(event.getOrderId())
                .customerId(event.getCustomerId())
                .employeeId(event.getEmployeeId())
                .productId(event.getProductId())
                .price(event.getPrice())
                .status(event.getStatus())
                .timeStamp(event.getTimeStamp())
                .timeOfIssue(event.getExpectedTimeOfIssue())
                .build();
    }

    public static Order eventToOrder(Event event, List<EventView> eventViewList) {
        return Order.builder()
                .orderId(event.getOrderId())
                .customerId(event.getCustomerId())
                .employeeId(event.getEmployeeId())
                .productId(event.getProductId())
                .price(event.getPrice())
                .status(event.getStatus())
                .registeredTime(event.getTimeStamp())
                .expectedTimeOfIssue(event.getTimeOfIssue())
                .events(eventViewList)
                .build();
    }

    public static List<EventView> toEventViewList(List<OrderEventView> orderEventViewList) {
        List<EventView> eventViewList = new ArrayList<>();
        if (orderEventViewList.isEmpty()) {
            return eventViewList;
        } else {
            for (OrderEventView orderEventView : orderEventViewList) {
                eventViewList.add(toEventView(orderEventView));
            }
            return eventViewList;
        }
    }

    private static EventView toEventView(OrderEventView orderEventView) {
        return EventView.builder()
                .oderId(orderEventView.getOrderId())
                .employeeId(orderEventView.getEmployeeId())
                .status(orderEventView.getStatus())
                .timeStamp(orderEventView.getTimeStamp())
                .build();
    }


    public static OrderEvent eventToOrderEvent(Event lastEvent) {
        return OrderEvent.builder()
                .orderId(lastEvent.getOrderId())
                .employeeId(lastEvent.getEmployeeId())
                .status(lastEvent.getStatus())
                .timeStamp(lastEvent.getTimeStamp())
                .build();
    }

}