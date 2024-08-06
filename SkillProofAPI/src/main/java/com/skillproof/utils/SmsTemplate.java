package com.skillproof.utils;

public class SmsTemplate {

    public static String getInvitationSms(String invitationLink) {
        return "You've been invited to join our platform! Accept your invitation: " + invitationLink;
    }

    public static String getUserCreationSms() {
        return "Your account has been successfully created. Welcome to our platform!";
    }

    public static String getPasswordResetSms(String resetLink) {
        return "Reset your password by clicking this link: " + resetLink;
    }

    public static String getNotificationSms(String notificationContent) {
        return "Notification: " + notificationContent;
    }
}

