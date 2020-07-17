package com.camunda.consulting.expense_mgt.process;

import com.camunda.consulting.expense_mgt.model.expenses.ExpenseReport;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

public class ProcessVariables {

    public static final ExpenseReport getExpenseReport(DelegateExecution delegateExecution){
        Object obj = delegateExecution.getVariable(com.camunda.consulting.expense_mgt.process.ProcessConstants.VARIABLE_EXPENSEREPORT_NAME);
        return (ExpenseReport) obj;
    }

    public static final void setExpenseReport(DelegateExecution delegateExecution, ExpenseReport expenseReport){
        ObjectValue expenseReportJson = Variables.objectValue(expenseReport)
                .create();
        delegateExecution.setVariable(com.camunda.consulting.expense_mgt.process.ProcessConstants.VARIABLE_EXPENSEREPORT_NAME, expenseReportJson);
    }

    public static final Long getApprovalLevel(DelegateExecution delegateExecution){
        return (Long) delegateExecution.getVariable(com.camunda.consulting.expense_mgt.process.ProcessConstants.VARIABLE_APPROVALLEVEL_NAME);
    }

    public static final void setApprovalLevel(DelegateExecution delegateExecution, Long approvalLevel){
        delegateExecution.setVariable(com.camunda.consulting.expense_mgt.process.ProcessConstants.VARIABLE_APPROVALLEVEL_NAME, approvalLevel);
    }
}
