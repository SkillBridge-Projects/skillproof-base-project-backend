package com.skillproof.services.email;

public interface EmailService {

    void sendEmail(String to, String subject, String text);

    void sendInvitationEmail(String to, String recipientName);

    void sendUserCreationEmail(String to, String recipientName);
}
