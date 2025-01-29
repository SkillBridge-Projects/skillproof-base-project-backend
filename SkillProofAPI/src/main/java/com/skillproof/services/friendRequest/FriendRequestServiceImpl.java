package com.skillproof.services.friendRequest;

import com.skillproof.enums.FriendRequestStatus;
import com.skillproof.model.entity.FriendRequest;
import com.skillproof.model.entity.User;
import com.skillproof.repositories.friendRequest.FriendRequestRepository;
import com.skillproof.repositories.user.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final JavaMailSender mailSender;

    public FriendRequestServiceImpl(UserRepository userRepository, FriendRequestRepository friendRequestRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.mailSender = mailSender;
    }

    @Override
    public String sendFriendRequest(String senderId, String receiverId) {
        User sender = userRepository.sender(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender with iD " + senderId + " not found "));
        User receiver = userRepository.receiver(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Sender with iD " + receiverId + " not found "));

        boolean exists = friendRequestRepository.existsBySenderAndReceiver(sender, receiver);
        if (exists) {
            return "Friend Request already sent";
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setStatus(FriendRequestStatus.PENDING);
        friendRequestRepository.save(friendRequest);
        return "Friend Request sent successfully";
    }

    @Override
    public String updateFriendRequestStatus(Long requestId, FriendRequestStatus status) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Friend Request with ID " + requestId + " not found"));

        friendRequest.setStatus(status);
        friendRequestRepository.save(friendRequest);

        if (status == FriendRequestStatus.ACCEPTED) {
            sendNotification(friendRequest.getSender().getEmailAddress(), friendRequest.getReceiver().getEmailAddress());
        }

        return "Friend Request status updated to " + status;
    }

    private void sendNotification(String senderEmail, String receiverEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(senderEmail);
            helper.setSubject("Friend Request Accepted");
            helper.setText("Your friend request to " + receiverEmail + " has been accepted.");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email notification", e);
        }
    }
}