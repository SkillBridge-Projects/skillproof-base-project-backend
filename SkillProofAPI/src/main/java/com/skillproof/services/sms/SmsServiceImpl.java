package com.skillproof.services.sms;

import com.skillproof.configuration.AppConfig;
import com.skillproof.utils.SmsTemplate;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SmsServiceImpl implements SmsService {

    private final AppConfig appConfig;

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    public SmsServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    private void sendSms(String toPhoneNumber, String message) {
        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber), message).create();
    }

    public void sendUserCreationSms(String phoneNumber) {
        String message = SmsTemplate.getUserCreationSms();
        sendSms(phoneNumber, message);
    }

    public void sendPasswordResetSms(String phoneNumber, String resetLink) {
        String message = SmsTemplate.getPasswordResetSms(resetLink);
        sendSms(phoneNumber, message);
    }

    public void sendNotificationSms(String phoneNumber, String notificationContent) {
        String message = SmsTemplate.getNotificationSms(notificationContent);
        sendSms(phoneNumber, message);
    }

    public void sendInvitationSms(String fromPhoneNumber){
        String message = SmsTemplate.getInvitationSms(appConfig.getInvitationLink());
        sendSms(fromPhoneNumber, message);
    }
}

