package com.skillproof.services.network;

import com.skillproof.model.entity.User;

import java.util.List;
import java.util.Set;

public interface NetworkService {

    List<User> search(Long id, String searchQuery);

    Set<User> getNetwork(Long id);

    Boolean hasSendRequest(Long id, Long otherUserId);

    void addToConnections(Long id, Long newUserId);

    void acceptConnection(Long id, Long connectionId);
}
