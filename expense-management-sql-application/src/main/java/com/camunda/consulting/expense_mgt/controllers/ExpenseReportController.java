package com.camunda.consulting.expense_mgt.controllers;

import com.camunda.consulting.expense_mgt.services.impl.ERProcessService;
import com.camunda.consulting.expense_mgt.model.expense.ExpenseReport;
import com.camunda.consulting.expense_mgt.repositories.ExpenseReportRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("expenses")
public class ExpenseReportController {

    private final ExpenseReportRepository repository;
    private final ERProcessService erProcessService;

    @PostMapping("/expense-reports")
    @Transactional
    public ExpenseReport save(@RequestBody ExpenseReport expenseReport) {
        ExpenseReport savedExpenseReport = repository.save(expenseReport);
        erProcessService.start(savedExpenseReport);
        return savedExpenseReport;
    }

    @GetMapping("/expense-reports")
    public List<ExpenseReport> findAll() {
        return IterableUtils.toList(repository.findAll());
    }
}
