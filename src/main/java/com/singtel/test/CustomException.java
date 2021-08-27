package com.singtel.test;

import com.singtel.test.mdl.CustomExceptionType;
import org.slf4j.helpers.MessageFormatter;

public class CustomException extends RuntimeException {

    private final CustomExceptionType customExceptionType;

    public CustomException(CustomExceptionType customExceptionType, String message, Object... args){
        super(MessageFormatter.arrayFormat(message, args).getMessage());
        this.customExceptionType = customExceptionType;
    }

}
