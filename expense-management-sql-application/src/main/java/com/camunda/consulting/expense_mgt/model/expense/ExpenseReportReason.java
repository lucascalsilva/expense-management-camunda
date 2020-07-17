package com.camunda.consulting.expense_mgt.model.expense;

public enum ExpenseReportReason {

    TRAINING("Training"), CONSULTING("Consulting"), POC("Proof of Concept"), EVENT("Event");

    private String description;

    ExpenseReportReason(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
