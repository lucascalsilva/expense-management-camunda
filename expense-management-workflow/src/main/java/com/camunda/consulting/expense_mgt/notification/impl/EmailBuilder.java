package com.camunda.consulting.expense_mgt.notification.impl;

import com.camunda.consulting.expense_mgt.config.TemplatesConfig;
import com.camunda.consulting.expense_mgt.exceptions.NotificationBuilderException;
import com.camunda.consulting.expense_mgt.model.common.Participant;
import com.camunda.consulting.expense_mgt.model.expenses.ExpenseReport;
import com.camunda.consulting.expense_mgt.model.notification.Email;
import com.camunda.consulting.expense_mgt.notification.NotificationBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailBuilder implements NotificationBuilder<Email, ExpenseReport, com.camunda.consulting.expense_mgt.notification.impl.EmailType> {

    private final Configuration freemarkerConfig;
    private final TemplatesConfig templatesConfig;

    public Email build(String key, ExpenseReport obj, com.camunda.consulting.expense_mgt.notification.impl.EmailType emailType, Participant participant) throws NotificationBuilderException {

        String templateName = templatesConfig.getEmailTemplate().replace(templatesConfig.getTypeVariable(), emailType.getTypeName());
        String subjectTempName =  templatesConfig.getSubjectTemplate().replace(templatesConfig.getTypeVariable(), emailType.getTypeName());

        Template emailTemplate;
        Template subjectTemplate;

        try {
            emailTemplate = emailTemplate = freemarkerConfig.getTemplate(templateName);
            subjectTemplate = freemarkerConfig.getTemplate(subjectTempName);
        }
        catch(IOException e){
            throw new NotificationBuilderException(e);
        }

        Map model = new HashMap<String,String>();
        model.put(key, obj);

        Email email = new Email();
        email.setTo(participant.getEmail());
        try {
            email.setSubject(FreeMarkerTemplateUtils.processTemplateIntoString(subjectTemplate, model));
            email.setContent(FreeMarkerTemplateUtils.processTemplateIntoString(emailTemplate, model));
        } catch (IOException | TemplateException e) {
            throw new NotificationBuilderException(e);
        }

        return email;
    }
}
