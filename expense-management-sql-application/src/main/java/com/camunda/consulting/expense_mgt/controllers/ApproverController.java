package com.camunda.consulting.expense_mgt.controllers;

import com.camunda.consulting.expense_mgt.model.common.Approver;
import com.camunda.consulting.expense_mgt.repositories.ApproverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.collections4.IterableUtils;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("common")
public class ApproverController {

    private final ApproverRepository repository;

    @GetMapping("/approvers")
    public List<Approver> findAll() {
        return IterableUtils.toList(repository.findAll());
    }
}
