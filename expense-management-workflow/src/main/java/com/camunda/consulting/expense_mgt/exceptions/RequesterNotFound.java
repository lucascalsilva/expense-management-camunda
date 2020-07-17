package com.camunda.consulting.expense_mgt.exceptions;

public class RequesterNotFound extends RuntimeException {

    public RequesterNotFound(String message){
        super(message);
    }
}
