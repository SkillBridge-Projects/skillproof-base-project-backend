package com.skillproof.services.sms;

public interface SmsService {

    void sendUserCreationSms(String phoneNumber);

    void sendInvitationSms(String fromPhoneNumber);
}
