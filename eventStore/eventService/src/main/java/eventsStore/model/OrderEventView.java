package eventsStore.model;

import dto.Status;

import java.time.LocalDateTime;

public interface OrderEventView {

    Integer getOrderId();

    Integer getEmployeeId();

    Status getStatus();

    LocalDateTime getTimeStamp();

}