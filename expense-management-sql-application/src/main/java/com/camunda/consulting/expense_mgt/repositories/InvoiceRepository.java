package com.camunda.consulting.expense_mgt.repositories;

import com.camunda.consulting.expense_mgt.model.accountspayable.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

}