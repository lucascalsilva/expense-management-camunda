package com.camunda.consulting.expense_mgt.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;

import static com.camunda.consulting.expense_mgt.util.ApplicationHelper.buildURI;

@Component
@RequiredArgsConstructor
@Getter
@Setter
@ConfigurationProperties(prefix = "services.erp")
public class ErpServicesConfig {

    private Long timeout;
    private String url;
    private final ErpServicesPathsConfig erpServicesPathsConfig;

    public URI getApproversServiceURI(){
        return buildURI(url, erpServicesPathsConfig.getApprovers());
    }

    public URI getInvoicesServiceURI(){
        return buildURI(url, erpServicesPathsConfig.getInvoices());
    }

    public URI getEmailsServiceURI(){
        return buildURI(url, erpServicesPathsConfig.getEmails());
    }
}
