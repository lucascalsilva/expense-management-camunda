package com.camunda.consulting.expense_mgt.notification;

import com.camunda.consulting.expense_mgt.exceptions.NotificationBuilderException;
import com.camunda.consulting.expense_mgt.model.common.Participant;

public interface NotificationBuilder<T,Y,Z> {

    T build(String key, Y obj, Z notificationType, Participant participant) throws NotificationBuilderException;


}
