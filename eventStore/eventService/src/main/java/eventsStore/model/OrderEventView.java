package eventsStore.model;

import java.time.LocalDateTime;

public interface OrderEventView {

    Integer getOrderId();

    Integer getEmployeeId();

    String getStatus();

    LocalDateTime getTimeStamp();

}