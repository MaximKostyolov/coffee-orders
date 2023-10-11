package eventsStore.controller;

import dto.Order;
import dto.OrderEvent;
import eventsStore.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/events")
public class EventsController {

    private final OrderService orderService;

    @Autowired
    public EventsController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createEvent(@Valid @RequestBody OrderEvent event, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        orderService.publishEvent(event);
    }

    @GetMapping("/order/{orderId}")
    public Order getOrderById(@PathVariable int orderId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return orderService.findOrder(orderId);
    }

    @GetMapping("/{orderId}")
    public OrderEvent getLastEventByOrderId(@PathVariable int orderId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return orderService.findLastOrderEvent(orderId);
    }

}