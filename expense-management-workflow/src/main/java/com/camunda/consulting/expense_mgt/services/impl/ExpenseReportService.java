package com.camunda.consulting.expense_mgt.services.impl;

import com.camunda.consulting.expense_mgt.config.ErpServicesConfig;
import com.camunda.consulting.expense_mgt.model.expenses.ExpenseReport;
import com.camunda.consulting.expense_mgt.services.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseReportService implements CrudService<ExpenseReport> {

    private final ErpServicesConfig erpServicesConfig;
    private final RestTemplate erpServicesRestTemplate;

    public void merge(ExpenseReport object) {

    }

    public List<ExpenseReport> findAll() {
        return null;
    }

    public ExpenseReport findById(Long id) {
        return null;
    }

    public void delete(Long id) {

    }
}
