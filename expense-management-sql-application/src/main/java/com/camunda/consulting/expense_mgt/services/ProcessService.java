package com.camunda.consulting.expense_mgt.services;

public interface ProcessService<T, ID> {

    void start(T object);
    void cancel(ID processId);
}
