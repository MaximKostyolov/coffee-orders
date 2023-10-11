package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventView {

    private Integer oderId;

    private Integer employeeId;

    private Status status;

    private LocalDateTime timeStamp;

}