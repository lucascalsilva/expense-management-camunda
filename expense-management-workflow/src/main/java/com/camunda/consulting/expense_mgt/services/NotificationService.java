package com.camunda.consulting.expense_mgt.services;

public interface NotificationService<T> {

    void send(T message);
}
