package com.camunda.consulting.expense_mgt.model.common;

public enum ApprovalStatus {
    APPROVED("Approved"), DECLINED("Declined"), REQUEST_CHANGES("Request Changes");

    private String description;

    ApprovalStatus(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
