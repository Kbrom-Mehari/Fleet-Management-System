package org.securityapps.vehicletracking.domain.commandlog;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.securityapps.vehicletracking.domain.enums.CommandStatus;

import java.time.LocalDateTime;

@Entity
public class CommandLog {
    @Id
    private Long id;
    private String command;
    private String response;
    private LocalDateTime sentAt;
    private LocalDateTime respondedAt;
    private CommandStatus status;  //need enum

}
