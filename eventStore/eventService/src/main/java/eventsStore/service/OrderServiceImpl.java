package eventsStore.service;

import dto.EventView;
import dto.Order;
import dto.OrderEvent;
import eventsStore.mapper.EventMapper;
import eventsStore.model.Event;
import eventsStore.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final EventRepository eventRepository;

    @Autowired
    public OrderServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void publishEvent(OrderEvent event) {
        Event newEvent = EventMapper.orderEventToEvent(event);
        eventRepository.save(newEvent);
    }

    @Override
    public Order findOrder(int id) {
        Event event = eventRepository.findRegisteredEvent(id);
        List<EventView> eventViewList = EventMapper.toEventViewList(eventRepository.findEventList(id));
        event.setStatus(eventRepository.findLastEvent(id).getStatus());
        Order order = EventMapper.eventToOrder(event, eventViewList);
        return order;
    }

    @Override
    public OrderEvent findLastOrderEvent(int orderId) {
        Event lastEvent = eventRepository.findLastEvent(orderId);
        return EventMapper.eventToOrderEvent(lastEvent);
    }

}