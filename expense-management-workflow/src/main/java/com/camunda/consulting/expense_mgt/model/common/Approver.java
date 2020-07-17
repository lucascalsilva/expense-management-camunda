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
public class Approver implements Serializable {

    private com.camunda.consulting.expense_mgt.model.common.Participant participant;

    private Long approvalLevel;
}
