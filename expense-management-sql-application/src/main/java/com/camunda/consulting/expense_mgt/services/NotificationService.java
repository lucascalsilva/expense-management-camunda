package com.camunda.consulting.expense_mgt.services;

import java.util.List;

public interface NotificationService<T> {

    void sendBatch(List<T> object);
    void send(T object);
}
