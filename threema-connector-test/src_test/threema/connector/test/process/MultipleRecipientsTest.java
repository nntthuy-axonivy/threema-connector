package threema.connector.test.process;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.ivy.scripting.objects.List;
import ch.ivyteam.threema.mocks.ThreemaServiceMock;
import threema.connector.ReceiverData;
import threema.connector.SendThreemaMessageData;
import util.LookupType;

@IvyProcessTest(enableWebServer = true)
public class MultipleRecipientsTest {

  private final static BpmProcess MULTIPLE_RECIPIENTS_PROCESS = BpmProcess.name("multipleRecipients");
  private final static String MESSAGE = "Hello World";

  @BeforeEach
  void setup(AppFixture fixture) {
    fixture.config("RestClients.ThreemaGateway.Url", ThreemaServiceMock.URI);
  }

  @Test
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
    assertThat(listReceiver).extracting(ReceiverData::getType)
            .containsExactly(LookupType.THREEMAID, LookupType.PHONE, LookupType.EMAIL);
    for (String status : msgData.getApiResponses()) {
      assertThat(status).contains("404");
    }
  }
}
