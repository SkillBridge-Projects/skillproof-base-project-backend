package com.skillproof.services.connection;

import com.skillproof.constants.MessageConstants;
import com.skillproof.constants.ObjectConstants;
import com.skillproof.enums.ConnectionStatus;
import com.skillproof.enums.NotificationType;
import com.skillproof.exceptions.ResourceNotFoundException;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.Connection;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.connection.ConnectionResponse;
import com.skillproof.model.request.connection.CreateConnectionRequest;
import com.skillproof.model.request.connection.UpdateConnectionRequest;
import com.skillproof.model.request.notification.CreateNotificationRequest;
import com.skillproof.model.request.user.UserResponse;
import com.skillproof.repositories.connection.ConnectionRepository;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.services.notification.NotificationService;
import com.skillproof.services.user.UserService;
import com.skillproof.utils.ResponseConverter;
import com.skillproof.utils.Utils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConnectionServiceImpl implements ConnectionService {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionServiceImpl.class);

    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final UserService userService;

    public ConnectionServiceImpl(ConnectionRepository connectionRepository, UserRepository userRepository,
                                 UserService userService, NotificationService notificationService) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Override
    public ConnectionResponse createConnection(CreateConnectionRequest createConnectionRequest) {
        LOG.debug("Start of createConnection method.");
        User userFollowing = userRepository.getUserById(createConnectionRequest.getFollowing());
        if (userFollowing == null) {
            LOG.error("User with id {} not found.", createConnectionRequest.getFollowing());
            throw new UserNotFoundException(ObjectConstants.USER, createConnectionRequest.getFollowing());
        }
        User follower = userRepository.getUserById(createConnectionRequest.getFollower());
        if (follower == null) {
            LOG.error("User with id {} not found.", createConnectionRequest.getFollower());
            throw new UserNotFoundException(ObjectConstants.USER, createConnectionRequest.getFollower());
        }
        Connection connection = createConnectionEntity(createConnectionRequest.getConnectionStatus(),
                userFollowing, follower);
        connection = connectionRepository.createConnection(connection);
        if (ObjectUtils.isNotEmpty(connection)) {
            String userName = getUserName(userFollowing);
            createNotificationWithMessage(follower.getId(), userFollowing.getProfilePicture(), userFollowing.getId(),
                    userName, connection.getConnectionStatus());
        }
        LOG.debug("End of createConnection method.");
        return getConnectionResponse(connection);
    }

    private String getUserName(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }

    private void createNotificationWithMessage(String followerId, String profile, String followingId, String userName,
                                               ConnectionStatus connectionStatus) {
        String msg = connectionStatus == ConnectionStatus.PENDING
                ? MessageConstants.REQUEST_SENT
                : MessageConstants.REQUEST_ACCEPTED;

        NotificationType type = connectionStatus == ConnectionStatus.PENDING
                ? NotificationType.CONNECTION_REQUEST
                : NotificationType.CONNECTION_ACCEPTED;
        String notificationMessage = Utils.getNotificationMessage(msg, userName);

        CreateNotificationRequest notificationRequest = new CreateNotificationRequest();
        notificationRequest.setRead(false);
        notificationRequest.setFollowerId(followerId);
        notificationRequest.setNotificationType(type);
        notificationRequest.setMessage(notificationMessage);
        notificationRequest.setProfilePicture(profile);
        notificationRequest.setFollowingId(followingId);
        notificationRequest.setFollowingUserName(userName);
        notificationService.createNotification(notificationRequest);
    }

    @Override
    public ConnectionResponse getConnectionForUser(String followingUserId, String followerId) {
        LOG.debug("Start of getConnectionForUser method.");
        User followingUser = userRepository.getUserById(followingUserId);
        if (ObjectUtils.isEmpty(followingUser)) {
            LOG.error("User with id {} not found.", followingUserId);
            throw new UserNotFoundException(ObjectConstants.USER, followingUserId);
        }

        User follower = userRepository.getUserById(followerId);
        if (ObjectUtils.isEmpty(follower)) {
            LOG.error("User with id {} not found.", followerId);
            throw new UserNotFoundException(ObjectConstants.USER, followerId);
        }

        Connection connection = connectionRepository.getConnectionForUser(followingUserId, followerId);
        LOG.debug("End of getConnectionForUser method.");
        return getConnectionResponse(connection);
    }

    @Override
    public ConnectionResponse updateConnection(Long id, UpdateConnectionRequest updateConnectionRequest) {
        LOG.debug("Start of updateConnection method.");
        Connection connection = connectionRepository.getConnectionById(id);
        if (ObjectUtils.isEmpty(connection)) {
            LOG.error("Connection with id {} not found.", id);
            throw new ResourceNotFoundException(ObjectConstants.CONNECTION, ObjectConstants.ID, id);
        }
        boolean isExists = connectionRepository.existsByFollowingIdAndFollowerIdAndStatus(
                connection.getFollowing().getId(), connection.getFollower().getId(),
                updateConnectionRequest.getConnectionStatus());
        if (!isExists) {
            //If the follower is accepting request means he is also following the following user
            //So, creating connection object for the follower (reciprocating the user)
            CreateConnectionRequest connectionRequest = new CreateConnectionRequest();
            connectionRequest.setConnectionStatus(updateConnectionRequest.getConnectionStatus());
            connectionRequest.setFollowing(connection.getFollower().getId());
            connectionRequest.setFollower(connection.getFollowing().getId());
            createConnection(connectionRequest);
        }
        connection.setConnectionStatus(updateConnectionRequest.getConnectionStatus());
        connection = connectionRepository.updateConnection(connection);
        LOG.debug("End of updateConnection method.");
        return getConnectionResponse(connection);
    }

    @Override
    public void deleteConnectionById(Long id) {
        LOG.debug("Start of deleteConnectionById method.");
        Connection connection = connectionRepository.getConnectionById(id);
        if (ObjectUtils.isEmpty(connection)) {
            LOG.error("Connection with id {} not found.", id);
            throw new ResourceNotFoundException(ObjectConstants.CONNECTION, ObjectConstants.ID, id);
        }
        List<Connection> connections = connectionRepository.findByFollowingIdOrFollowerIdAndConnectionStatus(
                connection.getFollowing().getId(), connection.getFollower().getId(), connection.getConnectionStatus());
        connections.forEach(con -> connectionRepository.deleteConnection(con.getId()));
        LOG.debug("End of deleteConnectionById method.");
    }

    @Override
    public ConnectionResponse getConnectionById(Long id) {
        LOG.debug("Start of getConnectionById method.");
        Connection connection = connectionRepository.getConnectionById(id);
        if (ObjectUtils.isEmpty(connection)) {
            LOG.error("Connection with id {} not found.", id);
            throw new ResourceNotFoundException(ObjectConstants.CONNECTION, ObjectConstants.ID, id);
        }
        return getConnectionResponse(connection);
    }

    @Override
    public List<UserResponse> getFollowingList(String userId) {
        LOG.debug("Start of getFollowingList method.");
        User user = userRepository.getUserById(userId);
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", userId);
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }
        List<Connection> connections = connectionRepository.findByFollowerAndStatus(userId,
                ConnectionStatus.ACCEPTED);
        return connections.stream()
                .map(connection -> userService.getUserById(connection.getFollowing().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getFollowersList(String userId) {
        LOG.debug("Start of getFollowersList method.");
        User user = userRepository.getUserById(userId);
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", userId);
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }
        List<Connection> connections = connectionRepository.findByFollowingAndStatus(userId,
                ConnectionStatus.ACCEPTED);
        return connections.stream()
                .map(connection -> userService.getUserById(connection.getFollower().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> listConnectionsForUser(String userId) {
        LOG.debug("Start of listConnectionsForUser method.");
        User user = userRepository.getUserById(userId);
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", userId);
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }
        return connectionRepository.listConnectionsForUser(userId).stream()
                .map(connection -> userService.getUserById(connection.getFollower().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public ConnectionResponse updateConnectionForUser(String followingUserId, String followerId) {
        ConnectionResponse response = getConnectionForUser(followingUserId, followerId);
        if (ObjectUtils.isEmpty(response)) {
            LOG.error("No Connection request exists between users {} and {}", followingUserId, followerId);
            throw new ResourceNotFoundException("No Connection request exists between users " + followingUserId + " and " + followerId);
        }
        UpdateConnectionRequest request = new UpdateConnectionRequest();
        request.setConnectionStatus(ConnectionStatus.ACCEPTED);
        return updateConnection(response.getId(), request);
    }

    private ConnectionResponse getConnectionResponse(Connection connection) {
        ConnectionResponse connectionResponse = ResponseConverter
                .copyProperties(connection, ConnectionResponse.class);
        connectionResponse.setFollowing(connection.getFollowing().getId());
        connectionResponse.setFollower(connection.getFollower().getId());
        return connectionResponse;
    }

    private List<ConnectionResponse> getResponseList(List<Connection> connections) {
        return connections.stream()
                .map(this::getConnectionResponse)
                .collect(Collectors.toList());
    }

    private Connection createConnectionEntity(ConnectionStatus connectionStatus, User userFollowing,
                                              User userFollowedBy) {
        Connection connection = new Connection();
        connection.setFollowing(userFollowing);
        connection.setFollower(userFollowedBy);
        connectionStatus = connectionStatus == null ? ConnectionStatus.PENDING : connectionStatus;
        connection.setConnectionStatus(connectionStatus);
        return connection;
    }
}
