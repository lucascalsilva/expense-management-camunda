package com.camunda.consulting.expense_mgt.notification.impl;

public enum EmailType {
    APPROVAL("approval"),
    PAYMENT_ISSUE("payment-issue"),
    EXPENSE_MANAGER("expense-manager");

    private String typeName;

    EmailType(String typeName){
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

}
