package com.camunda.consulting.expense_mgt.decision;

import com.camunda.consulting.expense_mgt.model.expenses.ExpenseReport;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;

import static com.camunda.consulting.expense_mgt.util.TestUtils.createRandomExpenseReport;
import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

public class DefineApprovalLevelDMNTest {

    private static final String DECISION_DEFINITION_KEY = "define-approval-level-er";

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();

    @Test
    @Deployment(resources = {"define-approval-level-er.dmn"})
    public void testApprovalLevelRule(){
        ExpenseReport expenseReport = createRandomExpenseReport();
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("amount", expenseReport.getTotalAmount());

        DmnDecisionTableResult decisionResult = rule.getDecisionService()
                .evaluateDecisionTableByKey(DECISION_DEFINITION_KEY)
                .variables(variables).evaluate();
        assertThat(decisionResult.getFirstResult()).contains(entry("approvalLevel", 1L));
    }
}
