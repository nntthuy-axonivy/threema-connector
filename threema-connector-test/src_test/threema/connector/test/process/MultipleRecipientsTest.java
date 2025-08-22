package threema.connector.test.process;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.utils.e2etest.context.MultiEnvironmentContextProvider;

import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.ivy.rest.client.RestClients;
import ch.ivyteam.ivy.scripting.objects.List;
import threema.connector.ReceiverData;
import threema.connector.SendThreemaMessageData;
import threema.connector.test.BaseSetup;
import util.LookupType;

@IvyProcessTest(enableWebServer = true)
@ExtendWith(MultiEnvironmentContextProvider.class)
public class MultipleRecipientsTest extends BaseSetup {

  private final static BpmProcess MULTIPLE_RECIPIENTS_PROCESS = BpmProcess.name("multipleRecipients");
  private final static BpmProcess SINGLE_RECIPIENT = BpmProcess.name("singleRecipient");
  private final static String MESSAGE = "Hello World";

  @AfterEach
  void afterEach(AppFixture fixture, IApplication app) {
    RestClients clients = RestClients.of(app);
    clients.remove("ThreemaGateway");
  }

  @TestTemplate
  void prepareMultipleRecipients(BpmClient bpmClient) {
    List<String> recipients = new List<String>();
    recipients.add("invalidThreemaID");
    recipients.add("41000000000");
    recipients.add("invalid@email.ch");
    BpmElement callable = MULTIPLE_RECIPIENTS_PROCESS.elementName("call(String,List<String>)");
    ExecutionResult result = bpmClient.start().subProcess(callable).execute(MESSAGE, recipients);
    SendThreemaMessageData msgData = result.data().last();
    List<ReceiverData> listReceiver = msgData.getReceiverData();
    assertThat(listReceiver.size()).isEqualTo(recipients.size());
    assertThat(listReceiver).extracting(ReceiverData::getType).containsExactly(LookupType.THREEMAID, LookupType.PHONE,
        LookupType.EMAIL);
    for (String status : msgData.getApiResponses()) {
      assertThat(status).contains("404");
    }
  }

  @TestTemplate
  void prepareSingleRecipient(BpmClient bpmClient, ExtensionContext context) {
    String receiverId = isRealTest ? "ECHOECHO" : "validId";
    BpmElement callable = SINGLE_RECIPIENT.elementName("call(String,String,LookupType)");
    ExecutionResult result = bpmClient.start().subProcess(callable).execute(MESSAGE, receiverId, LookupType.THREEMAID);
    ReceiverData msgData = result.data().last();
    assertThat(msgData.getApiResponse()).isEqualTo("Sent successfully (200)");
  }
}
