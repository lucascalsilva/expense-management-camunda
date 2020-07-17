package com.camunda.consulting.expense_mgt.process;

import com.camunda.consulting.expense_mgt.delegates.ApproversDelegate;
import com.camunda.consulting.expense_mgt.delegates.NotificationDelegate;
import com.camunda.consulting.expense_mgt.delegates.ProcessPaymentDelegate;
import com.camunda.consulting.expense_mgt.delegates.UpdateERDelegate;
import com.camunda.consulting.expense_mgt.model.common.ApprovalStatus;
import com.camunda.consulting.expense_mgt.model.expenses.ExpenseReport;
import com.camunda.consulting.expense_mgt.notification.NotificationBuilder;
import com.camunda.consulting.expense_mgt.services.CrudService;
import com.camunda.consulting.expense_mgt.services.NotificationService;
import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static com.camunda.consulting.expense_mgt.process.ProcessConstants.PROCESS_EXPENSEREPORT_KEY;
import static com.camunda.consulting.expense_mgt.process.ProcessConstants.VARIABLE_EXPENSEREPORT_NAME;
import static com.camunda.consulting.expense_mgt.util.TestUtils.*;
import static com.camunda.consulting.expense_mgt.util.TestUtils.createRandomExpenseReport;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.mockito.Mockito.when;

@Deployment(resources = {"define-approval-level-er.dmn", "expense-report-process.bpmn", "payment-process.bpmn"})
public class ExpenseReportApprovalBPMNTest {

  /*static {
    LogFactory.useSlf4jLogging(); // MyBatis
  }*/

  /*@Rule @ClassRule
  public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();*/

  @Rule
  public ProcessEngineRule rule = new ProcessEngineRule();

  @Mock
  private CrudService approverService;

  @Mock
  private CrudService expenseReportService;

  @Mock
  private NotificationService notificationService;

  @Mock
  private NotificationBuilder notificationBuilder;
  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  @Before
  public void setup(){
    init(rule.getProcessEngine());
    MockitoAnnotations.initMocks(this);
    when(approverService.findAll()).thenReturn(createRandomApprovers());
    Mocks.register("approversDelegate", new ApproversDelegate(approverService));
    Mocks.register("notificationDelegate", new NotificationDelegate(notificationBuilder, notificationService));
    Mocks.register("processPaymentDelegate", new ProcessPaymentDelegate());
    Mocks.register("updateERDelegate", new UpdateERDelegate(expenseReportService));
  }

  @Test
  public void testApprovePath(){
    ExpenseReport expenseReport = createRandomExpenseReport();
    ObjectValue objectValue = Variables.objectValue(expenseReport).create();

    ProcessInstance processInstance = runtimeService()
            .startProcessInstanceByKey(PROCESS_EXPENSEREPORT_KEY, expenseReport.getNumber(),
                    withVariables(VARIABLE_EXPENSEREPORT_NAME, objectValue));

    logger.info("Expense Report: {}", expenseReport.toString());
    assertThat(processInstance).hasVariables(VARIABLE_EXPENSEREPORT_NAME);
    assertThat(processInstance).hasPassed("DefineApprovalLevelTask");
    assertThat(processInstance).hasPassed("DefineApproversTask");
    assertThat(processInstance).isWaitingAt("ApproveExpenseReportTask");

    expenseReport.setLastApproval(ApprovalStatus.APPROVED);

    HashMap<String, Object> approvalVariables = new HashMap<String, Object>();
    approvalVariables.put(VARIABLE_EXPENSEREPORT_NAME, expenseReport);

    taskQuery().active().list().forEach(task -> complete(task, approvalVariables));

    assertThat(processInstance).hasPassed("NotifyApprovalTask");
    assertThat(processInstance).hasPassed("UpdateExpenseStatusApprovedTask");
    assertThat(processInstance).isWaitingAt("PaymentProcessCallActivity");
    ProcessInstance paymentProcess = calledProcessInstance();
    assertThat(paymentProcess).hasPassed("SendPaymentRequestTask");
    assertThat(paymentProcess).isWaitingAt("WaitForPaymentConfirmationTask");

    runtimeService().createMessageCorrelation("MessagePaymentConfirmation")
            .processInstanceBusinessKey(expenseReport.getNumber())
            .setVariable("paymentCorrect", true).correlate();

    assertThat(paymentProcess).isEnded();
    assertThat(processInstance).isEnded();
  }

  @Test
  public void testDeclinePath() {
    ExpenseReport expenseReport = createRandomExpenseReport();
    ObjectValue objectValue = Variables.objectValue(expenseReport).create();

    expenseReport.setLastApproval(ApprovalStatus.DECLINED);

    ProcessInstance processInstance = runtimeService()
            .createProcessInstanceByKey(PROCESS_EXPENSEREPORT_KEY)
            .setVariable(VARIABLE_EXPENSEREPORT_NAME, objectValue)
            .businessKey(expenseReport.getNumber())
            .startBeforeActivity("NotifyDeclineTask")
            .execute();

    assertThat(processInstance).hasPassed("NotifyDeclineTask");
    assertThat(processInstance).hasPassed("UpdateExpenseStatusDeclinedTask");
    assertThat(processInstance).isEnded();
  }

  @Test
  public void testChangesRequested() {
    ExpenseReport expenseReport = createRandomExpenseReport();
    ObjectValue objectValue = Variables.objectValue(expenseReport).create();

    ProcessInstance processInstance = runtimeService()
            .startProcessInstanceByKey(PROCESS_EXPENSEREPORT_KEY, expenseReport.getNumber(),
                    withVariables(VARIABLE_EXPENSEREPORT_NAME, objectValue));

    assertThat(processInstance).hasPassed("DefineApprovalLevelTask");
    assertThat(processInstance).hasPassed("DefineApproversTask");
    assertThat(processInstance).isWaitingAt("ApproveExpenseReportTask");

    expenseReport.setChangesDescription("Some changes are needed...");
    expenseReport.setLastApproval(ApprovalStatus.REQUEST_CHANGES);

    runtimeService().createMessageCorrelation(com.camunda.consulting.expense_mgt.process.ProcessConstants.MESSAGE_REQUESTCHANGES)
            .processInstanceBusinessKey(expenseReport.getNumber())
            .setVariable(VARIABLE_EXPENSEREPORT_NAME, expenseReport)
            .correlate();

    assertThat(processInstance).isWaitingAt("ChangesReceivedEvent");

    runtimeService().createMessageCorrelation(com.camunda.consulting.expense_mgt.process.ProcessConstants.MESSAGE_CHANGESRECEIVED)
            .processInstanceBusinessKey(expenseReport.getNumber())
            .correlate();

    assertThat(processInstance).hasPassed("DefineApprovalLevelTask");
    assertThat(processInstance).hasPassed("DefineApproversTask");
    assertThat(processInstance).isWaitingAt("ApproveExpenseReportTask");
  }

  @Test
  public void testProcessCanceled() {
    ExpenseReport expenseReport = createRandomExpenseReport();
    ObjectValue objectValue = Variables.objectValue(expenseReport).create();

    ProcessInstance processInstance = runtimeService()
            .startProcessInstanceByKey(PROCESS_EXPENSEREPORT_KEY, expenseReport.getNumber(),
                    withVariables(VARIABLE_EXPENSEREPORT_NAME, objectValue));

    assertThat(processInstance).hasPassed("DefineApprovalLevelTask");
    assertThat(processInstance).hasPassed("DefineApproversTask");
    assertThat(processInstance).isWaitingAt("ApproveExpenseReportTask");

    expenseReport.setLastApproval(ApprovalStatus.CANCELLED);

    runtimeService().createMessageCorrelation(com.camunda.consulting.expense_mgt.process.ProcessConstants.MESSAGE_CANCELPROCESS)
            .processInstanceBusinessKey(expenseReport.getNumber())
            .setVariable(VARIABLE_EXPENSEREPORT_NAME, expenseReport)
            .correlate();

    assertThat(processInstance).hasPassed("NotifyProcessCancelationTask");
    assertThat(processInstance).isEnded();
  }

  @Test
  public void testPaymentIssue() {
    ExpenseReport expenseReport = createRandomExpenseReport();
    ObjectValue objectValue = Variables.objectValue(expenseReport).create();

    expenseReport.setLastApproval(ApprovalStatus.APPROVED);

    ProcessInstance processInstance = runtimeService()
            .createProcessInstanceByKey(PROCESS_EXPENSEREPORT_KEY)
            .setVariable(VARIABLE_EXPENSEREPORT_NAME, objectValue)
            .businessKey(expenseReport.getNumber())
            .startBeforeActivity("NotifyApprovalTask")
            .execute();

    assertThat(processInstance).hasPassed("NotifyApprovalTask");
    assertThat(processInstance).hasPassed("UpdateExpenseStatusApprovedTask");
    assertThat(processInstance).isWaitingAt("PaymentProcessCallActivity");
    ProcessInstance paymentProcess = calledProcessInstance();
    assertThat(paymentProcess).hasPassed("SendPaymentRequestTask");
    assertThat(paymentProcess).isWaitingAt("WaitForPaymentConfirmationTask");

    expenseReport.setLastApproval(ApprovalStatus.PAYMENT_ISSUES);

    runtimeService().createMessageCorrelation(com.camunda.consulting.expense_mgt.process.ProcessConstants.MESSAGE_WAITPAYMENTCONFIRMATION)
            .processInstanceBusinessKey(expenseReport.getNumber())
            .setVariable(com.camunda.consulting.expense_mgt.process.ProcessConstants.VARIABLE_PAYMENTCORRECT_NAME, false)
            .setVariable(VARIABLE_EXPENSEREPORT_NAME, expenseReport)
            .correlate();

    assertThat(paymentProcess).isEnded();
    assertThat(processInstance).hasPassed("NotifyPaymentIssueTask");
    complete(task(), withVariables(com.camunda.consulting.expense_mgt.process.ProcessConstants.VARIABLE_ISSUESHANDLED_NAME, false));

    assertThat(processInstance).isEnded();
  }

  @Test
  public void testProcessDeadlineOverdue() {
    ExpenseReport expenseReport = createRandomExpenseReport();
    ObjectValue objectValue = Variables.objectValue(expenseReport).create();

    expenseReport.setLastApproval(ApprovalStatus.APPROVED);

    ProcessInstance processInstance = runtimeService()
            .createProcessInstanceByKey(PROCESS_EXPENSEREPORT_KEY)
            .setVariable(VARIABLE_EXPENSEREPORT_NAME, objectValue)
            .businessKey(expenseReport.getNumber())
            .startBeforeActivity("NotifyApprovalTask")
            .execute();

    Job job = managementService().createJobQuery().processInstanceId(processInstance.getProcessInstanceId()).timers().singleResult();
    execute(job);


    assertThat(processInstance).hasPassed("NotifyExpenseManagerTask");

    assertThat(processInstance).hasPassed("NotifyApprovalTask");
    assertThat(processInstance).hasPassed("UpdateExpenseStatusApprovedTask");
    assertThat(processInstance).isWaitingAt("PaymentProcessCallActivity");
    ProcessInstance paymentProcess = calledProcessInstance();
    assertThat(paymentProcess).hasPassed("SendPaymentRequestTask");
    assertThat(paymentProcess).isWaitingAt("WaitForPaymentConfirmationTask");

    runtimeService().createMessageCorrelation("MessagePaymentConfirmation")
            .processInstanceBusinessKey(expenseReport.getNumber())
            .setVariable("paymentCorrect", true).correlate();

    assertThat(paymentProcess).isEnded();
    assertThat(processInstance).isEnded();

  }
}
