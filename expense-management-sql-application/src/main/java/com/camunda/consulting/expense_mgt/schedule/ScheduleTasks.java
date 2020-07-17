package com.camunda.consulting.expense_mgt.schedule;

import com.camunda.consulting.expense_mgt.services.impl.EmailService;
import com.camunda.consulting.expense_mgt.model.notification.Email;
import com.camunda.consulting.expense_mgt.repositories.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.camunda.consulting.expense_mgt.util.PropertyNames.SCHEDULER_EMAIL_DELAYTIME;

@Component
@RequiredArgsConstructor
public class ScheduleTasks {

    private final EmailRepository repository;
    private final EmailService emailAdapter;

    @Scheduled(fixedDelayString = SCHEDULER_EMAIL_DELAYTIME)
    public void sendEmails(){
        List<Email> emailsToSend = repository.findBySent(false);
        if(emailsToSend.size() > 0){
            List<Email> emailsSent = emailAdapter.sendBatch(emailsToSend);
            repository.saveAll(emailsSent);
        }
    }
}
