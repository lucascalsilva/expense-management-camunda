package com.camunda.consulting.expense_mgt.model.common;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Approval implements Serializable {

    private com.camunda.consulting.expense_mgt.model.common.ApprovalStatus approvalStatus;
    private com.camunda.consulting.expense_mgt.model.common.Approver approver;
}
