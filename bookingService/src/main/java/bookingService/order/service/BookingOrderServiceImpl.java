package bookingService.order.service;

import bookingService.employee.model.Employee;
import bookingService.employee.service.EmployeeService;
import bookingService.exception.NotFoundException;
import bookingService.exception.UnsupportedStateException;
import bookingService.order.dto.NewOrderDto;
import bookingService.order.mapper.OrderMapper;
import bookingService.product.model.Product;
import bookingService.product.repository.ProductJpaRepository;
import client.OrderClient;
import client.OrderEventClient;
import dto.Order;
import dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Service
@Transactional
public class BookingOrderServiceImpl implements BookingOrderService {

    private final EmployeeService employeeService;

    private final ProductJpaRepository productRepository;

    private static Integer orderId = 0;

    @Value("${event-store.url}")
    private static String serverUrl = "http://localhost:9090";

    private static final OrderEventClient orderEventClient = new OrderEventClient(serverUrl, new RestTemplateBuilder());

    private static final OrderClient orderClient = new OrderClient(serverUrl, new RestTemplateBuilder());

    @Autowired
    public BookingOrderServiceImpl(EmployeeService employeeService, ProductJpaRepository productRepository) {
        this.employeeService = employeeService;
        this.productRepository = productRepository;
    }

    public static Integer getOrderId() {
        return orderId;
    }

    public static void setOrderId(Integer orderId) {
        BookingOrderServiceImpl.orderId = orderId;
    }

    @Override
    public void createOrder(NewOrderDto newOrderDto) {
        setOrderId(getOrderId() + 1);
        OrderEvent orderEvent = OrderMapper.orderDtoToOrderEvent(newOrderDto);
        Product product = productRepository.findById(newOrderDto.getProductId()).orElseThrow(() ->
                new NotFoundException("Product with id = " + newOrderDto.getProductId() + " not found"));
        orderEvent.setPrice(product.getPrice());
        orderEvent.setOrderId(getOrderId());
        Employee employee = employeeService.getAvaibleEmployee();
        orderEvent.setEmployeeId(employee.getId());
        LocalDateTime timeToAvailable = employee.getTimeToAvailable();
        if (timeToAvailable == null) {
            sendAcceptedEventRightNow(orderEvent, product, employee);
        } else {
            if (timeToAvailable.isBefore(LocalDateTime.now())) {
                sendAcceptedEventRightNow(orderEvent, product, employee);
            } else {
                sendAcceptedEventAfterEmployeeAvailable(orderEvent, product, employee);
            }
        }
    }

    @Override
    public void cancelOrder(int orderId) {
        OrderEvent orderEvent = getOrderEventById(orderId);
        if (orderEvent.getStatus().equals("CANCELLED")) {
            throw new UnsupportedStateException("Order with id = " + orderId + " already cancelled");
        } else if (orderEvent.getStatus().equals("ISSUE")) {
            throw new UnsupportedStateException("Order with id = " + orderId + " already issue");
        } else {
            orderEvent.setStatus("CANCELLED");
            orderEvent.setTimeStamp(LocalDateTime.now());
            orderEventClient.post("", new HashMap<>(), orderEvent);
        }
    }

    @Override
    public Order getOrderById(int orderId) {
        Map<String, Object> parametrs = Map.of("orderId", orderId);
        return orderClient.get("/{orderId}", parametrs).getBody();
    }

    @Override
    public void issueOrder(int orderId) {
        OrderEvent orderEvent = getOrderEventById(orderId);
        if (orderEvent.getStatus().equals("CANCELLED")) {
            throw new UnsupportedStateException("Order with id = " + orderId + " already cancelled");
        } else if (orderEvent.getStatus().equals("ISSUE")) {
            throw new UnsupportedStateException("Order with id = " + orderId + " already issue");
        } else if (orderEvent.getStatus().equals("READY")) {
            orderEvent.setStatus("ISSUE");
            orderEvent.setTimeStamp(LocalDateTime.now());
            orderEventClient.post("", new HashMap<>(), orderEvent);
        } else {
            throw new UnsupportedStateException("Order with id = " + orderId + " is not ready yet");
        }
    }

    private void sendAcceptedEventRightNow(OrderEvent orderEvent, Product product, Employee employee) {
        OrderEvent registeredEvent = orderEvent;
        registeredEvent.setStatus("REGISTERED");
        registeredEvent.setExpectedTimeOfIssue(LocalDateTime.now().plusSeconds(product.getCookingTime()));
        orderEventClient.post("", new HashMap<>(), registeredEvent);
        employee.setTimeToAvailable(orderEvent.getExpectedTimeOfIssue());
        employee.setCountOrder(employee.getCountOrder() + 1);
        employeeService.updateEmployee(employee.getId(), employee);
        sendAcceptedEvent(orderEvent);
        Thread thread = new Thread();
        thread.start();
        Timer timer = new Timer();
        timer.schedule(new TimeTaskReady(orderEvent.getOrderId(), thread, timer),
                Date.from(orderEvent.getExpectedTimeOfIssue().atZone(ZoneId.systemDefault()).toInstant()));
    }

    private void sendAcceptedEventAfterEmployeeAvailable(OrderEvent orderEvent, Product product, Employee employee) {
        OrderEvent registeredEvent = orderEvent;
        registeredEvent.setStatus("REGISTERED");
        registeredEvent.setExpectedTimeOfIssue(employee.getTimeToAvailable().plusSeconds(product.getCookingTime()));
        orderEventClient.post("", new HashMap<>(), registeredEvent);
        Thread thread = new Thread();
        thread.start();
        Timer timer = new Timer(true);
        timer.schedule(new TimeTaskAccepted(orderEvent.getOrderId(), thread, timer),
                Date.from(employee.getTimeToAvailable().atZone(ZoneId.systemDefault()).toInstant()));
        employee.setTimeToAvailable(orderEvent.getExpectedTimeOfIssue());
        employee.setCountOrder(employee.getCountOrder() + 1);
        employeeService.updateEmployee(employee.getId(), employee);
        Thread threadReady = new Thread();
        threadReady.start();
        Timer timerReady = new Timer();
        timer.schedule(new TimeTaskReady(orderEvent.getOrderId(), threadReady, timerReady),
                Date.from(orderEvent.getExpectedTimeOfIssue().atZone(ZoneId.systemDefault()).toInstant()));
    }

    private static OrderEvent getOrderEventById(Integer orderId) {
        Map<String, Object> parametrs = Map.of("orderId", orderId);
        return orderEventClient.get("/" + orderId, parametrs).getBody();
    }

    private static void sendAcceptedEvent(OrderEvent orderEvent) {
        OrderEvent acceptedEvent = OrderEvent.builder()
                .orderId(orderEvent.getOrderId())
                .employeeId(orderEvent.getEmployeeId())
                .status("ACCEPTED")
                .timeStamp(LocalDateTime.now())
                .build();
        orderEventClient.post("", new HashMap<>(), acceptedEvent);
    }

    private static void sendReadyEvent(OrderEvent orderEvent) {
        OrderEvent readyEvent = OrderEvent.builder()
                .orderId(orderEvent.getOrderId())
                .employeeId(orderEvent.getEmployeeId())
                .status("READY")
                .timeStamp(LocalDateTime.now())
                .build();
        orderEventClient.post("", new HashMap<>(), readyEvent);
    }

    private static class TimeTaskAccepted extends TimerTask {

        private int orderId;

        private Thread thread;

        private Timer timer;

        public TimeTaskAccepted(int orderId, Thread thread, Timer timer) {
            this.orderId = orderId;
            this.thread = thread;
            this.timer = timer;
        }

        @Override
        public void run() {
            OrderEvent orderEvent = getOrderEventById(orderId);
            if (!orderEvent.getStatus().equals("CANCELLED")) {
                sendAcceptedEvent(orderEvent);
            }
            thread.interrupt();
            timer.cancel();
        }

    }

    private static class TimeTaskReady extends TimerTask {

        private int orderId;

        private Thread thread;

        private Timer timer;

        public TimeTaskReady(int orderId, Thread thread, Timer timer) {
            this.orderId = orderId;
            this.thread = thread;
            this.timer = timer;
        }

        @Override
        public void run() {
            OrderEvent orderEvent = getOrderEventById(orderId);
            if (!orderEvent.getStatus().equals("CANCELLED")) {
                sendReadyEvent(orderEvent);
            }
            thread.interrupt();
            timer.cancel();
        }

    }

}