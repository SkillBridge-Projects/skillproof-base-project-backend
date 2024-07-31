package com.skillproof.repositories.connection;

import com.skillproof.enums.ConnectionStatus;
import com.skillproof.model.entity.Connection;
import com.skillproof.repositories.ConnectionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConnectionRepositoryImpl implements ConnectionRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionRepositoryImpl.class);

    private final ConnectionDao connectionDao;

    public ConnectionRepositoryImpl(ConnectionDao connectionDao) {
        this.connectionDao = connectionDao;
    }

    @Override
    public Connection createConnection(Connection connection) {
        return connectionDao.saveAndFlush(connection);
    }

    @Override
    public Connection getConnectionForUser(String followingUserId, String followedByUserId) {
        return connectionDao.findByFollowingIdAndFollowerId(followingUserId, followedByUserId);
    }

    @Override
    public Connection getConnectionById(Long id) {
        return connectionDao.findById(id).orElse(null);
    }

    @Override
    public Connection updateConnection(Connection connection) {
        return connectionDao.saveAndFlush(connection);
    }

    @Override
    public void deleteConnection(Long id) {
        connectionDao.deleteById(id);
    }

    @Override
    public List<Connection> findByFollowingAndStatus(String followingId, ConnectionStatus connectionStatus) {
        return connectionDao.findByFollowingIdAndConnectionStatus(followingId, connectionStatus);
    }

    @Override
    public List<Connection> findByFollowerAndStatus(String followerId, ConnectionStatus connectionStatus) {
        return connectionDao.findByFollowerIdAndConnectionStatus(followerId, connectionStatus);
    }

    @Override
    public boolean existsByFollowingIdAndFollowerIdAndStatus(String followingId, String followerId,
                                                             ConnectionStatus status) {
        return connectionDao.existsByFollowingIdAndFollowerIdAndConnectionStatus(followingId, followerId, status);
    }

    @Override
    public List<Connection> listConnectionsForUser(String userId) {
        return connectionDao.findByFollowingIdAndConnectionStatus(userId, ConnectionStatus.ACCEPTED);
    }
}
