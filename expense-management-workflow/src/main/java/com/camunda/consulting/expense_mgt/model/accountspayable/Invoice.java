package com.camunda.consulting.expense_mgt.model.accountspayable;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Invoice implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;
    private Date creationDate;
    private Double totalAmount;
    private Date dueDate;
    private String description;
    private List<com.camunda.consulting.expense_mgt.model.accountspayable.InvoiceLine> lines;
}
