package com.skillproof.services.connection;

import com.skillproof.model.request.connection.ConnectionResponse;
import com.skillproof.model.request.connection.CreateConnectionRequest;
import com.skillproof.model.request.connection.UpdateConnectionRequest;
import com.skillproof.model.request.user.UserResponse;

import java.util.List;

public interface ConnectionService {
    ConnectionResponse createConnection(CreateConnectionRequest createConnectionRequest);

    ConnectionResponse getConnectionForUser(String followingUserId, String followedByUserId);

    ConnectionResponse updateConnection(Long id, UpdateConnectionRequest updateConnectionRequest);

    void deleteConnectionById(Long id);

    ConnectionResponse getConnectionById(Long id);

    List<UserResponse> getFollowingList(String userId);

    List<UserResponse> getFollowersList(String userId);

    List<UserResponse> listConnectionsForUser(String userId);

    ConnectionResponse updateConnectionForUser(String followingUserId, String followerId);
}
