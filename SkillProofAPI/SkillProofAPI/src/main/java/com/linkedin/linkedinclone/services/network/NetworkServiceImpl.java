package com.linkedin.linkedinclone.services.network;

import com.linkedin.linkedinclone.constants.ErrorMessageConstants;
import com.linkedin.linkedinclone.constants.ObjectConstants;
import com.linkedin.linkedinclone.exceptions.ResourceNotFoundException;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.*;
import com.linkedin.linkedinclone.repositories.ChatRepository;
import com.linkedin.linkedinclone.repositories.ConnectionRepository;
import com.linkedin.linkedinclone.repositories.NotificationRepository;
import com.linkedin.linkedinclone.services.user.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.linkedin.linkedinclone.utils.PictureSave.decompressBytes;
import static com.linkedin.linkedinclone.utils.Utils.minDistance;

@Service
@AllArgsConstructor
public class NetworkServiceImpl implements NetworkService {

    private final UserServiceImpl userService;
    private final ConnectionRepository connectionRepository;
    private final ChatRepository chatRepository;
    private final NotificationRepository notificationRepository;
    @Override
    public List<User> search(Long id, String searchQuery) {
        User user = userService.getUserById(id);
        List<User> searchResults = new ArrayList<User>();
        List<User> allUsers = userService.getAllUsers();
        String[] searchQueries = searchQuery.split("\\W+");
        MultiValueMap<Integer, User> map = new LinkedMultiValueMap<>();

        for (String s : searchQueries) {
            String w = s.toLowerCase();
            for (User u : allUsers) {
                if ((u.getId() != id) && (!u.getName().equals("admin"))) {
                    int dist;
                    if ((dist = minDistance(w, u.getName().toLowerCase(Locale.ROOT))) < 10) {
                        map.add(dist, u);
                    } else if ((dist = minDistance(w, u.getSurname().toLowerCase(Locale.ROOT))) < 10) {
                        map.add(dist, u);
                    } else if ((u.getCurrentCompany() != null && u.getCurrentCompany().toLowerCase(Locale.ROOT) == w)
                            || (u.getCurrentJob() != null && u.getCurrentJob().toLowerCase(Locale.ROOT) == w)) {
                        map.add(1, u);
                    }
                }
            }
        }

        for (Map.Entry m : map.entrySet()) {
            searchResults.addAll((List<User>) m.getValue());
        }

        for (User u : searchResults) {
            Picture uPic = u.getProfilePicture();
            if (uPic != null && uPic.isCompressed()) {
                Picture temp = new Picture(uPic.getName(), uPic.getType(), decompressBytes(uPic.getBytes()));
                u.setProfilePicture(temp);
            }
        }
        return searchResults;
    }

    @Override
    public Set<User> getNetwork(Long userId) {
        User currentUser = userService.getUserById(userId);
        Set<User> network = new HashSet<>();
        Set<Connection> connectionsFollowing = currentUser.getUsersFollowing();
        for (Connection con : connectionsFollowing) {
            if (con.getIsAccepted()) {
                User userinNetwork = con.getUserFollowed();
                network.add(userinNetwork);
            }
        }

        Set<Connection> connectionsFollowedBy = currentUser.getUserFollowedBy();
        for (Connection con : connectionsFollowedBy) {
            if (con.getIsAccepted()) {
                User userinNetwork = con.getUserFollowing();
                network.add(userinNetwork);
            }
        }
        for (User u : network) {
            Picture uPic = u.getProfilePicture();
            if (uPic != null && uPic.isCompressed()) {
                Picture temp = new Picture(uPic.getName(), uPic.getType(), decompressBytes(uPic.getBytes()));
                temp.setCompressed(false);
                u.setProfilePicture(temp);
            }
        }
        return network;
    }

    @Override
    public Boolean hasSendRequest(Long id, Long otherUserId) {
        User currentUser = userService.getUserById(id);
        User otherUser = userService.getUserById(otherUserId);

        Set<Connection> connectionsFollowing = currentUser.getUsersFollowing();
        for (Connection con : connectionsFollowing) {
            if (!con.getIsAccepted() && con.getUserFollowed() == otherUser) {
                return true;
            }
        }

        connectionsFollowing = currentUser.getUserFollowedBy();
        for (Connection con : connectionsFollowing) {
            if (!con.getIsAccepted() && con.getUserFollowing() == otherUser) {
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
        Connection conn = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.NOTIFICATION, connectionId)));
        conn.setIsAccepted(true);
        connectionRepository.save(conn);

        Notification not = notificationRepository.findByConnectionId(connectionId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.NOTIFICATION, connectionId)));
        not.setIsShown(true);
        notificationRepository.save(not);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy:HH.mm.ss");

        Chat chat = new Chat();
        chat.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Set<User> users = new HashSet<>();
        users.add(user);
        if (conn.getUserFollowed() != user)
            users.add(conn.getUserFollowed());
        else if (conn.getUserFollowing() != user)
            users.add(conn.getUserFollowing());
        chat.setUsers(users);
        chatRepository.save(chat);
    }
}
