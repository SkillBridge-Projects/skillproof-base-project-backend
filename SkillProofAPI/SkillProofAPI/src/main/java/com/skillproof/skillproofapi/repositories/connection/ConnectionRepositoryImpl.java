package com.skillproof.skillproofapi.repositories.connection;

import com.skillproof.skillproofapi.model.entity.Connection;
import com.skillproof.skillproofapi.repositories.ConnectionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
}
