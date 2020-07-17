package com.camunda.consulting.expense_mgt.delegates;

import com.camunda.consulting.expense_mgt.mappers.ApprovalMapper;
import com.camunda.consulting.expense_mgt.model.common.Approver;
import com.camunda.consulting.expense_mgt.model.expenses.ExpenseReport;
import com.camunda.consulting.expense_mgt.services.CrudService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.camunda.consulting.expense_mgt.process.ProcessVariables.*;

@Component
@RequiredArgsConstructor
public class ApproversDelegate implements JavaDelegate {

    private final CrudService<Approver> crudService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Map<String, Object> variables = delegateExecution.getVariables();


        ExpenseReport expenseReport = getExpenseReport(delegateExecution);
        Long approvalLevel = getApprovalLevel(delegateExecution);

        List<Approver> approvers = crudService.findAll()
                .stream()
                .filter(approver -> approver.getApprovalLevel() <= approvalLevel)
                .collect(Collectors.toList());

        expenseReport.setApprovals(ApprovalMapper.INSTANCE.approversToApprovals(approvers));
        setExpenseReport(delegateExecution, expenseReport);
    }
}
