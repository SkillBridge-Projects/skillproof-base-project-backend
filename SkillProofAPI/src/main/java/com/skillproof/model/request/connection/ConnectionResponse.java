package com.skillproof.model.request.connection;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ConnectionResponse extends CreateConnectionRequest {

    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
