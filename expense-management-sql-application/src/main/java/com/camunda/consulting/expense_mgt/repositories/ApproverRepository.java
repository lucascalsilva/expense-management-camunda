package com.camunda.consulting.expense_mgt.repositories;

import com.camunda.consulting.expense_mgt.model.common.Approver;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproverRepository extends CrudRepository<Approver, Long> {

}