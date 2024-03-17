package com.skillproof.skillproofapi.services.network;

import com.skillproof.skillproofapi.constants.ErrorMessageConstants;
import com.skillproof.skillproofapi.constants.ObjectConstants;
import com.skillproof.skillproofapi.exceptions.ResourceNotFoundException;
import com.skillproof.skillproofapi.exceptions.UserNotFoundException;
import com.skillproof.skillproofapi.repositories.ChatDao;
import com.skillproof.skillproofapi.repositories.ConnectionDao;
import com.skillproof.skillproofapi.repositories.NotificationDao;
import com.skillproof.skillproofapi.services.user.UserServiceImpl;
import com.skillproof.skillproofapi.model.entity.*;
import com.skillproof.skillproofapi.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NetworkServiceImpl implements NetworkService {

    private final UserServiceImpl userService;
    private final ConnectionDao connectionDao;
    private final ChatDao chatDao;
    private final NotificationDao notificationDao;
    @Override
    public List<User> search(Long id, String searchQuery) {
        List<User> allUsers = userService.getAllUsers();

        return allUsers.stream()
                .filter(u -> u.getId() != id && !"admin".equals(u.getUserName()))
                .filter(u -> isMatch(searchQuery, u.getUserName().toLowerCase()))
//                .map(this::updateProfilePicture)
                .collect(Collectors.toList());
    }

    private boolean isMatch(String searchQuery, String username) {
        String[] searchQueries = searchQuery.toLowerCase().split("\\W+");
        for (String query : searchQueries) {
            if (Utils.minDistance(query, username) < 10) {
                return true;
            }
        }
        return false;
    }

//    private User updateProfilePicture(User user) {
//        Picture profilePicture = user.getProfilePicture();
//        if (profilePicture != null && profilePicture.isCompressed()) {
//            Picture decompressedPicture = new Picture(profilePicture.getName(), profilePicture.getType(), PictureSave.decompressBytes(profilePicture.getBytes()));
//            user.setProfilePicture(decompressedPicture);
//        }
//        return user;
//    }

    @Override
    public Set<User> getNetwork(Long userId) {
        User currentUser = userService.getUserById(userId);
        Set<User> network = new HashSet<>();
        Collection<Connection> connectionsFollowing = currentUser.getUsers();
        for (Connection con : connectionsFollowing) {
            if (con.getIsAccepted()) {
                User userinNetwork = con.getUser();
                network.add(userinNetwork);
            }
        }

        Collection<Connection> connectionsFollowedBy = currentUser.getUsers();
        for (Connection con : connectionsFollowedBy) {
            if (con.getIsAccepted()) {
                User userinNetwork = con.getUser();
                network.add(userinNetwork);
            }
        }
//        for (User u : network) {
//            Picture uPic = u.getProfilePicture();
//            if (uPic != null && uPic.isCompressed()) {
//                Picture temp = new Picture(uPic.getName(), uPic.getType(), PictureSave.decompressBytes(uPic.getBytes()));
//                temp.setCompressed(false);
//                u.setProfilePicture(temp);
//            }
//        }
        return network;
    }

    @Override
    public Boolean hasSendRequest(Long id, Long otherUserId) {
        User currentUser = userService.getUserById(id);
        User otherUser = userService.getUserById(otherUserId);

        Collection<Connection> connectionsFollowing = currentUser.getUsers();
        for (Connection con : connectionsFollowing) {
            if (!con.getIsAccepted() && con.getUser() == otherUser) {
                return true;
            }
        }

        connectionsFollowing = currentUser.getUsers();
        for (Connection con : connectionsFollowing) {
            if (!con.getIsAccepted() && con.getUser() == otherUser) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addToConnections(Long userId, Long newUserId) {
        User user = userService.getUserById(userId);
        userService.newConnection(user, newUserId);
    }

    @Override
    public void acceptConnection(Long userId, Long connectionId) {
        User user = userService.getUserById(userId);
        Connection conn = connectionDao.findById(connectionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.NOTIFICATION, connectionId)));
        conn.setIsAccepted(true);
        connectionDao.save(conn);

        Notification not = notificationDao.findByConnectionId(connectionId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.NOTIFICATION, connectionId)));
        not.setIsShown(true);
        notificationDao.save(not);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy:HH.mm.ss");

        Chat chat = new Chat();
        chat.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Set<User> users = new HashSet<>();
        users.add(user);
        if (conn.getUser() != user)
            users.add(conn.getUser());
        chat.setUsers(users);
        chatDao.save(chat);
    }
}
