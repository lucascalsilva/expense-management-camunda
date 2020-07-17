package com.camunda.consulting.expense_mgt.mappers;

import com.camunda.consulting.expense_mgt.model.common.Approval;
import com.camunda.consulting.expense_mgt.model.common.Approver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper
public abstract class ApprovalMapper {
    public static ApprovalMapper INSTANCE = Mappers.getMapper(ApprovalMapper.class);

    public abstract List<Approval> approversToApprovals(List<Approver> approvers);

    @Mapping(target = "approver", source = "approver")
    public abstract Approval approverToApproval(Approver approver);

}
