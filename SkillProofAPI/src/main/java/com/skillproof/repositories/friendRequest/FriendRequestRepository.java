package com.skillproof.repositories.friendRequest;

import com.skillproof.model.entity.FriendRequest;
import com.skillproof.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    boolean existsBySenderAndReceiver(User sender, User receiver);
}
