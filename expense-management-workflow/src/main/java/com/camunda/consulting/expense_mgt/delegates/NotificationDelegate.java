package com.camunda.consulting.expense_mgt.delegates;

import com.camunda.consulting.expense_mgt.model.common.Participant;
import com.camunda.consulting.expense_mgt.model.expenses.ExpenseReport;
import com.camunda.consulting.expense_mgt.model.notification.Email;
import com.camunda.consulting.expense_mgt.notification.NotificationBuilder;
import com.camunda.consulting.expense_mgt.notification.impl.EmailType;
import com.camunda.consulting.expense_mgt.process.ProcessConstants;
import com.camunda.consulting.expense_mgt.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.camunda.consulting.expense_mgt.process.NotificationVariables.getEmailType;
import static com.camunda.consulting.expense_mgt.process.NotificationVariables.getParticipant;
import static com.camunda.consulting.expense_mgt.process.ProcessVariables.getExpenseReport;

@Component
@RequiredArgsConstructor
public class NotificationDelegate implements JavaDelegate {

    private final NotificationBuilder<Email, ExpenseReport, EmailType> notificationBuilder;
    private final NotificationService<Email> notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Participant participant = getParticipant(delegateExecution);
        EmailType emailType = getEmailType(delegateExecution);

        Email email = notificationBuilder.build(ProcessConstants.VARIABLE_EXPENSEREPORT_NAME,
                getExpenseReport(delegateExecution), emailType, participant);
        notificationService.send(email);
    }
}
