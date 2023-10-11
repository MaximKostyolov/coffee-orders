package bookingService.order.controller;

import bookingService.order.dto.NewOrderDto;
import bookingService.order.service.BookingOrderService;
import dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    private final BookingOrderService bookingOrderService;

    @Autowired
    public OrderController(BookingOrderService bookingOrderService) {
        this.bookingOrderService = bookingOrderService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createOrder(@Valid @RequestBody NewOrderDto newOrderDto, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        bookingOrderService.createOrder(newOrderDto);
    }

    @PatchMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable int orderId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        bookingOrderService.cancelOrder(orderId);
    }

    @PatchMapping("/{orderId}/issue")
    public void issueOrder(@PathVariable int orderId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        bookingOrderService.issueOrder(orderId);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable int orderId, HttpServletRequest request) {
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return bookingOrderService.getOrderById(orderId);
    }

}