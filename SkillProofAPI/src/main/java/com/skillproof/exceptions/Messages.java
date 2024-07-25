package com.skillproof.exceptions;

import org.slf4j.helpers.MessageFormatter;

public final class Messages {

    private Messages(){}

    public static final String ALREADY_EXISTS = "{} with {} '{}' already exists.";
    public static final String NOT_FOUND = "{} with {} '{}' not found.";

    public static String msg(String msg, Object... msgArgs){
        if (msg.contains("%s")){
            msg = msg.replace("%s", "{}");
        }
        return MessageFormatter.arrayFormat(msg, msgArgs).getMessage();
    }
}
