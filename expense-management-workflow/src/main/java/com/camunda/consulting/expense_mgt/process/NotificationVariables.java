package com.camunda.consulting.expense_mgt.process;

import com.camunda.consulting.expense_mgt.model.common.Participant;
import com.camunda.consulting.expense_mgt.notification.impl.EmailType;
import org.camunda.bpm.engine.delegate.DelegateExecution;

public class NotificationVariables {

    public static Participant getParticipant(DelegateExecution delegateExecution){
        return (Participant) delegateExecution.getVariableLocal(com.camunda.consulting.expense_mgt.process.ProcessConstants.VARIABLELOCAL_PARTICIPANT_NAME);
    }

    public static EmailType getEmailType(DelegateExecution delegateExecution){
        return EmailType.valueOf((String) delegateExecution.getVariableLocal(com.camunda.consulting.expense_mgt.process.ProcessConstants.VARIABLELOCAL_EMAILTYPE_NAME));
    }
}
