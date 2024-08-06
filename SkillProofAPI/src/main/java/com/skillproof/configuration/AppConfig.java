package com.skillproof.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class AppConfig {

    @Value("${spring.mail.username}")
    private String mailUserName;

    @Value("${skillproof.mail.from}")
    private String from;

    @Value("${skillproof.mail.invitation}")
    private String invitationLink;

//    @Value("${twilio.account.sid}")
//    private String twilioSid;
//
//    @Value("${twilio.auth.token}")
//    private String twilioAuthToken;
//
//    @Value("${twilio.phone.number}")
//    private String twilioMobileNumber;
}
