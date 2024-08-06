package com.skillproof.utils;

public class EmailTemplate {

    // Invitation Email Template
    public static String getInvitationEmail(String recipientName, String invitationLink) {
        return "Dear " + recipientName + ",\n\n" +
                "You have been invited to join our platform. Please click the link below to accept the invitation:\n\n" +
                invitationLink + "\n\n" +
                "Best regards,\n" +
                "Team SkillProof";
    }

    // User Creation Success Email Template
    public static String getUserCreationEmail(String recipientName) {
        return "Dear " + recipientName + ",\n\n" +
                "Your account has been successfully created on our platform. We're excited to have you on board!\n\n" +
                "Best regards,\n" +
                "Team SkillProof";
    }

    // Password Reset Email Template
    public static String getPasswordResetEmail(String recipientName, String resetLink) {
        return "Dear " + recipientName + ",\n\n" +
                "We received a request to reset your password. Please click the link below to reset your password:\n\n" +
                resetLink + "\n\n" +
                "If you did not request a password reset, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Team SkillProof";
    }

    // Generic Notification Email Template
    public static String getNotificationEmail(String recipientName, String notificationContent) {
        return "Dear " + recipientName + ",\n\n" +
                notificationContent + "\n\n" +
                "Best regards,\n" +
                "Team SkillProof";
    }
}

