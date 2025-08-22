package threema.connector.test.process;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.utils.e2etest.context.MultiEnvironmentContextProvider;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.History;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import threema.connector.ReceiverData;
import threema.connector.test.BaseSetup;
import util.LookupType;

@IvyProcessTest(enableWebServer = true)
@ExtendWith(MultiEnvironmentContextProvider.class)
public class GetReceiverInfoTest extends BaseSetup {

  private static final BpmProcess RECEIVER_INFO_PROCESS = BpmProcess.name("getReceiverInfo");
  private static final String VALID_ID = "validId";

  @TestTemplate
  void getIDByValidEmail(ExtensionContext context, BpmClient bpmClient) {
    BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
    String email = VALID_ID;
    ReceiverData recDatMail = new ReceiverData();
    recDatMail.setIdentifier(email);
    recDatMail.setType(LookupType.EMAIL);
    ExecutionResult resultMail = bpmClient.start().subProcess(callable).execute(recDatMail);
    ReceiverData resultDataMail = resultMail.data().last();
    History historyMail = resultMail.history();
    if (isRealTest) {
      assertThat(resultDataMail.getApiResponse()).isEqualTo("ID-Lookup: 404");
    } else {
      assertThat(resultDataMail.getApiResponse()).contains("200");
      assertThat(historyMail.elementNames()).contains("call(receiverData)").contains("LookupId")
          .contains("LookupPubKey");
    }
  }

  @TestTemplate
  void getIDByInvalidEmail(ExtensionContext context, BpmClient bpmClient) {
    BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
    String email = "invalid@email.com";
    ReceiverData recDatMail = new ReceiverData();
    recDatMail.setIdentifier(email);
    recDatMail.setType(LookupType.EMAIL);
    ExecutionResult resultMail = bpmClient.start().subProcess(callable).execute(recDatMail);
    ReceiverData resultDataMail = resultMail.data().last();
    History historyMail = resultMail.history();
    assertThat(resultDataMail.getApiResponse()).contains("404");
    assertThat(historyMail.elementNames()).contains("call(receiverData)").contains("LookupId")
        .doesNotContain("LookupPubKey");
  }

  @TestTemplate
  void getIDByValidPhone(ExtensionContext context, BpmClient bpmClient) {
    BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
    String phone = VALID_ID;
    ReceiverData recDatMail = new ReceiverData();
    recDatMail.setIdentifier(phone);
    recDatMail.setType(LookupType.PHONE);
    ExecutionResult resultMail = bpmClient.start().subProcess(callable).execute(recDatMail);
    ReceiverData resultDataMail = resultMail.data().last();
    History historyMail = resultMail.history();
    if (isRealTest) {
      assertThat(resultDataMail.getApiResponse()).isEqualTo("ID-Lookup: 404");
    } else {
      assertThat(resultDataMail.getApiResponse()).contains("200");
      assertThat(historyMail.elementNames()).contains("call(receiverData)").contains("LookupId")
          .contains("LookupPubKey");
    }
  }

  @TestTemplate
  void getPublicKeyByID(ExtensionContext context, BpmClient bpmClient) {
    BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
    String threemaId = isRealTest ? "ECHOECHO" : "validId";
    ReceiverData recDatId = new ReceiverData();
    recDatId.setIdentifier(threemaId);
    recDatId.setType(LookupType.THREEMAID);
    ExecutionResult resultId = bpmClient.start().subProcess(callable).execute(recDatId);
    ReceiverData resultDataId = resultId.data().last();
    History historyId = resultId.history();
    if (isRealTest) {
      assertThat(resultDataId.getApiResponse()).isEqualTo("PublicKey-Lookup: 200");
    } else {
      assertThat(resultDataId.getApiResponse()).contains("200");
 /**
  * Dear Bug Hunter,
  * This credential is intentionally included for educational purposes only and does not provide access to any production systems.
  * Please do not submit it as part of our bug bounty program.
  */
      assertThat(resultDataId.getPublicKey())
          .isEqualTo("ffbb40cfced42f75c4d83c7d35300c0698bf3ef1ab49ace323a1bbc38ee23f36");
      assertThat(historyId.elementNames()).contains("call(receiverData)").contains("LookupPubKey")
          .doesNotContain("LookupId");
    }
  }

  @TestTemplate
  void getPublicKeyByInvalidID(ExtensionContext context, BpmClient bpmClient) {
    BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
    String threemaId = isRealTest ? "ECHOECHO" : "invalidID";
    ReceiverData recDatId = new ReceiverData();
    recDatId.setIdentifier(threemaId);
    recDatId.setType(LookupType.THREEMAID);
    ExecutionResult resultId = bpmClient.start().subProcess(callable).execute(recDatId);
    ReceiverData resultDataId = resultId.data().last();
    History historyId = resultId.history();
    if (isRealTest) {
      assertThat(resultDataId.getApiResponse()).isEqualTo("PublicKey-Lookup: 200");
    } else {
      assertThat(resultDataId.getApiResponse()).contains("404");
      assertThat(historyId.elementNames()).contains("call(receiverData)").contains("LookupPubKey")
          .doesNotContain("LookupId");
    }
  }
}
