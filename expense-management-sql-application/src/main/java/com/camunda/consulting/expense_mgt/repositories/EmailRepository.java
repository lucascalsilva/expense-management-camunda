package com.camunda.consulting.expense_mgt.repositories;

import com.camunda.consulting.expense_mgt.model.notification.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends CrudRepository<Email, Long> {

    List<Email> findBySent(Boolean sent);
}
