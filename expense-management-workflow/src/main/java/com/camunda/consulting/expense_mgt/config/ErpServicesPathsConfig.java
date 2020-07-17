package com.camunda.consulting.expense_mgt.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
@Setter
@ConfigurationProperties(prefix = "services.erp.paths")
public class ErpServicesPathsConfig {

    private String approvers;
    private String invoices;
    private String emails;
}
