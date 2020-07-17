package com.camunda.consulting.expense_mgt.controllers;

import com.camunda.consulting.expense_mgt.model.notification.Email;
import com.camunda.consulting.expense_mgt.services.impl.EmailService;
import com.camunda.consulting.expense_mgt.repositories.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("notification")
public class EmailController {

    private final EmailRepository repository;
    private final EmailService emailService;

    @PostMapping("/emails")
    public Email send(@RequestBody Email email) {
        return repository.save(email);
    }

}
