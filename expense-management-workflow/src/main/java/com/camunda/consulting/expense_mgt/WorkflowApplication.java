package com.camunda.consulting.expense_mgt;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication("expense-management-workflow")
public class WorkflowApplication {
  public static void main(String... args) {
    SpringApplication.run(WorkflowApplication.class, args);
  }
}
