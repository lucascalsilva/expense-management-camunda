package com.camunda.consulting.expense_mgt.delegates;

import com.camunda.consulting.expense_mgt.model.expenses.ExpenseReport;
import com.camunda.consulting.expense_mgt.services.CrudService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.camunda.consulting.expense_mgt.process.ProcessVariables.getExpenseReport;

@Component
@RequiredArgsConstructor
public class UpdateERDelegate implements JavaDelegate {

    private final CrudService<ExpenseReport> crudServiceAdapter;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ExpenseReport expenseReport = getExpenseReport(delegateExecution);
        crudServiceAdapter.merge(expenseReport);
    }
}
