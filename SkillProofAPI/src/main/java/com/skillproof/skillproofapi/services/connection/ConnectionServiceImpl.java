//package com.skillproof.skillproofapi.services.connection;
//
//import com.skillproof.skillproofapi.constants.ObjectConstants;
//import com.skillproof.skillproofapi.enumerations.NotificationType;
//import com.skillproof.skillproofapi.exceptions.UserNotFoundException;
//import com.skillproof.skillproofapi.model.entity.Connection;
//import com.skillproof.skillproofapi.model.entity.Notification;
//import com.skillproof.skillproofapi.model.entity.User;
//import com.skillproof.skillproofapi.model.request.connection.ConnectionResponse;
//import com.skillproof.skillproofapi.model.request.connection.CreateConnectionRequest;
//import com.skillproof.skillproofapi.repositories.connection.ConnectionRepository;
//import com.skillproof.skillproofapi.repositories.notification.NotificationRepository;
//import com.skillproof.skillproofapi.repositories.user.UserRepository;
//import com.skillproof.skillproofapi.utils.ResponseConverter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@Transactional
//public class ConnectionServiceImpl implements ConnectionService {
//
//    private static final Logger LOG = LoggerFactory.getLogger(ConnectionServiceImpl.class);
//
//    private final ConnectionRepository connectionRepository;
//    private final UserRepository userRepository;
//    private final NotificationRepository notificationRepository;
//
//    public ConnectionServiceImpl(ConnectionRepository connectionRepository, UserRepository userRepository,
//                                 NotificationRepository notificationRepository) {
//        this.connectionRepository = connectionRepository;
//        this.userRepository = userRepository;
//        this.notificationRepository = notificationRepository;
//    }
//
//
//    @Override
//    public ConnectionResponse createConnection(Long userId, CreateConnectionRequest createConnectionRequest) {
//        LOG.info("Start of createConnection method.");
//        User user = userRepository.getUserById(userId);
//        if (user == null){
//            throw new UserNotFoundException(ObjectConstants.USER, userId);
//        }
//        User connectingUser = userRepository.getUserById(createConnectionRequest.getConnectionUserId());
//        if (connectingUser == null){
//            throw new UserNotFoundException(ObjectConstants.USER, createConnectionRequest.getConnectionUserId());
//        }
//        Connection connection = createConnectionEntity(createConnectionRequest.isAccepted(), connectingUser);
//        connection = connectionRepository.createConnection(connection);
//        notificationRepository.createNotification(createNotificationEntity(connection));
//        LOG.info("Start of createConnection method.");
//        return getConnectionResponse(connection);
//    }
//
//    @Override
//    public List<ConnectionResponse> listConnectionsForUser(Long userId) {
//        User user = userRepository.getUserById(userId);
//        List<Connection> connections = (List<Connection>) user;
//        return ResponseConverter.copyListProperties(connections, ConnectionResponse.class);
//    }
//
//    private ConnectionResponse getConnectionResponse(Connection connection) {
//        ConnectionResponse connectionResponse = ResponseConverter.copyProperties(connection, ConnectionResponse.class);
////        connectionResponse.setConnectionUserId(Long.valueOf(connection.getUser().getId()));
//        return connectionResponse;
//    }
//
//    private Notification createNotificationEntity(Connection connection) {
//        Notification notification = new Notification();
//        notification.setNotificationType(NotificationType.CONNECTION_REQUEST);
//        notification.setConnectionRequest(connection);
////        notification.setUser(connection.getUser());
//        notification.setIsShown(false);
//        notification.setCreatedDate(LocalDateTime.now());
//        notification.setUpdatedDate(LocalDateTime.now());
//        return notification;
//    }
//
//    private Connection createConnectionEntity(boolean isAccepted, User user) {
//        Connection connection = new Connection();
////        connection.setUser(user);
//        connection.setIsAccepted(isAccepted);
//        connection.setCreatedDate(LocalDateTime.now());
//        connection.setUpdatedDate(LocalDateTime.now());
//        return connection;
//    }
//}
