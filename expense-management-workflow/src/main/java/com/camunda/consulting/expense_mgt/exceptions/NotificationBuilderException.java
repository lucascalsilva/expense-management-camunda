package com.camunda.consulting.expense_mgt.exceptions;

public class NotificationBuilderException extends RuntimeException {

    public NotificationBuilderException(String message){
        super(message);
    }

    public NotificationBuilderException(Exception e){
        super(e);
    }
}
