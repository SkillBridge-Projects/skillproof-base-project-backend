package com.skillproof.services.friendRequest;

import com.skillproof.enums.FriendRequestStatus;

public interface FriendRequestService {

    String sendFriendRequest (String senderId, String receiverId);

    String updateFriendRequestStatus(Long requestId, FriendRequestStatus status);

}