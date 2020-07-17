package com.camunda.consulting.expense_mgt.services.impl;

import com.camunda.consulting.expense_mgt.services.ProcessService;
import com.camunda.consulting.expense_mgt.config.ProcessEngineServicesConfig;
import com.camunda.consulting.expense_mgt.model.expense.ExpenseReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.camunda.consulting.expense_mgt.util.ApplicationConstants.VARIABLE_EXPENSEREPORTNUM_NAME;

@Service
@RequiredArgsConstructor
public class ERProcessService implements ProcessService<ExpenseReport, String> {

    private final ProcessEngineServicesConfig processEngineServicesConfig;

    private final RestTemplate restTemplate;

    public void start(ExpenseReport expenseReport) {
        restTemplate.postForEntity(processEngineServicesConfig.getExpenseReportProcessStartURI(), expenseReport, ExpenseReport.class);
    }

    public void cancel(String erNumber) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(VARIABLE_EXPENSEREPORTNUM_NAME, erNumber);
        restTemplate.postForLocation(processEngineServicesConfig.getExpenseReportProcessCancelURI().toString(), null, params);
    }
}