package com.linkedin.linkedinclone.services.network;

import com.linkedin.linkedinclone.model.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

public interface NetworkService {

    List<User> search(Long id, String searchQuery);

    Set<User> getNetwork(Long id);

    Boolean hasSendRequest(Long id, Long otherUserId);

    void addToConnections(Long id, Long newUserId);

    void acceptConnection(Long id, Long connectionId);
}
