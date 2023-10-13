package eventsStore.mapper;

import dto.EventView;
import dto.Order;
import dto.OrderEvent;
import eventsStore.model.Event;

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

    public static List<EventView> toEventViewList(List<Event> eventList) {
        List<EventView> eventViewList = new ArrayList<>();
        if (eventList.isEmpty()) {
            return eventViewList;
        } else {
            for (Event event : eventList) {
                eventViewList.add(toEventView(event));
            }
            return eventViewList;
        }
    }

    private static EventView toEventView(Event event) {
        return EventView.builder()
                .oderId(event.getOrderId())
                .employeeId(event.getEmployeeId())
                .status(event.getStatus())
                .timeStamp(event.getTimeStamp())
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