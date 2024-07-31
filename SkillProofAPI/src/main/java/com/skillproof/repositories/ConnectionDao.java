package com.skillproof.repositories;

import com.skillproof.enums.ConnectionStatus;
import com.skillproof.model.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionDao extends JpaRepository<Connection, Long> {

    Connection findByFollowingIdAndFollowerId(String following, String followerId);

    List<Connection> findByFollowingIdOrFollowerIdAndConnectionStatus(String following, String followerId,
                                                                      ConnectionStatus connectionStatus);

    List<Connection> findByFollowingIdAndConnectionStatus(String followingId, ConnectionStatus connectionStatus);

    List<Connection> findByFollowerIdAndConnectionStatus(String followerId, ConnectionStatus connectionStatus);

    boolean existsByFollowingIdAndFollowerIdAndConnectionStatus(String followingId, String followerId,
                                                                ConnectionStatus connectionStatus);
}
