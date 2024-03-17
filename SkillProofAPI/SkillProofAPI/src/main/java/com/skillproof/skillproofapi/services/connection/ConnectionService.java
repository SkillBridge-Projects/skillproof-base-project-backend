package com.skillproof.skillproofapi.services.connection;

import com.skillproof.skillproofapi.model.request.connection.ConnectionResponse;
import com.skillproof.skillproofapi.model.request.connection.CreateConnectionRequest;

import java.util.List;

public interface ConnectionService {
    ConnectionResponse createConnection(Long userId, CreateConnectionRequest createConnectionRequest);

    List<ConnectionResponse> listConnectionsForUser(Long userId);
}
