package com.skillproof.repositories.connection;

import com.skillproof.enums.ConnectionStatus;
import com.skillproof.model.entity.Connection;

import java.util.List;

public interface ConnectionRepository {
    Connection createConnection(Connection connection);

    Connection getConnectionForUser(String followingUserId, String followedByUserId);

    Connection getConnectionById(Long id);

    Connection updateConnection(Connection connection);

    void deleteConnection(Long id);

    List<Connection> findByFollowingAndStatus(String followingId, ConnectionStatus connectionStatus);

    List<Connection> findByFollowerAndStatus(String followerId, ConnectionStatus connectionStatus);

    boolean existsByFollowingIdAndFollowerIdAndStatus(String followingId, String followerId,
                                                      ConnectionStatus status);

    List<Connection> listConnectionsForUser(String userId);
}
