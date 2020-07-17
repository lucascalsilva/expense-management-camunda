package com.camunda.consulting.expense_mgt.repositories;

import com.camunda.consulting.expense_mgt.model.expense.ExpenseReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseReportRepository extends JpaRepository<ExpenseReport, Long> {
}
