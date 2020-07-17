package com.camunda.consulting.expense_mgt.controllers;

import com.camunda.consulting.expense_mgt.model.accountspayable.Invoice;
import com.camunda.consulting.expense_mgt.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("accounts-payable")
public class InvoiceController {

    private final InvoiceRepository repository;

    @PostMapping("/invoices")
    public Invoice merge(@RequestBody Invoice invoice) {
        return repository.save(invoice);
    }

    @GetMapping("/invoices")
    public List<Invoice> findAll() {
        return IterableUtils.toList(repository.findAll());
    }

}
