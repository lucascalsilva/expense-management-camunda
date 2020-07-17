package com.camunda.consulting.expense_mgt.model.notification;

import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Email implements Serializable {

    private String to;
    private String cc;
    private String cco;
    private String subject;
    private String content;
}
