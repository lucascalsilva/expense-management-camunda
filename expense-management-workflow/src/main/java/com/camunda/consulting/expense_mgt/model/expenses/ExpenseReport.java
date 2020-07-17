package com.camunda.consulting.expense_mgt.model.expenses;

import com.camunda.consulting.expense_mgt.model.common.Approval;
import com.camunda.consulting.expense_mgt.model.common.ApprovalStatus;
import com.camunda.consulting.expense_mgt.model.common.Participant;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ExpenseReport implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;
    private String number;
    private Participant requester;
    private Participant expenseManager;
    private com.camunda.consulting.expense_mgt.model.expenses.ExpenseReportReason reason;
    private String justification;
    private Date creationDate;
    private Double totalAmount;
    private List<Approval> approvals;
    private List<com.camunda.consulting.expense_mgt.model.expenses.Expense> expenses;
    private ApprovalStatus lastApproval;
    private String changesDescription;
}
