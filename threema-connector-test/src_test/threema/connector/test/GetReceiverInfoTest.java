package threema.connector.test;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.History;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import threema.connector.receiverData;
import util.LookupType;

@IvyProcessTest
public class GetReceiverInfoTest {
	
	private static final BpmProcess RECEIVER_INFO_PROCESS = BpmProcess.name("getReceiverInfo");
	private static final String ECHO_PUBLIC_KEY = "4a6a1b34dcef15d43cb74de2fd36091be99fbbaf126d099d47d83d919712c72b";
	
	@Test
	void getIDByInvalidEmail(BpmClient bpmClient) {
		BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
		
		String email = "invalid@email.com";
		receiverData recDatMail = new receiverData();
		recDatMail.setIdentifier(email);
		recDatMail.setType(LookupType.EMAIL);
		
		ExecutionResult resultMail = bpmClient.start().subProcess(callable).execute(recDatMail);
		receiverData resultDataMail = resultMail.data().last();		
		History historyMail = resultMail.history();

		assertThat(resultDataMail.getApiResponse()).isEqualTo("404");
		assertThat(historyMail.elementNames()).contains("call(receiverData)");
		assertThat(historyMail.elementNames()).contains("LookupId");
		assertThat(historyMail.elementNames()).doesNotContain("LookupPubKey");
		
	}
	
	@Test
	void getPublicKeyByID(BpmClient bpmClient) {
		BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
		
		String threemaId = "ECHOECHO";
		receiverData recDatId = new receiverData();
		recDatId.setIdentifier(threemaId);
		recDatId.setType(LookupType.THREEMAID);
		
		ExecutionResult resultId = bpmClient.start().subProcess(callable).execute(recDatId);
		receiverData resultDataId = resultId.data().last();
		History historyId = resultId.history();
		
		assertThat(resultDataId.getApiResponse()).isEqualTo("200");
 /**
  * Dear Bug Hunter,
  * This credential is intentionally included for educational purposes only and does not provide access to any production systems.
  * Please do not submit it as part of our bug bounty program.
  */
		assertThat(resultDataId.getPublicKey()).isEqualTo(ECHO_PUBLIC_KEY);
		assertThat(historyId.elementNames()).contains("call(receiverData)");
		assertThat(historyId.elementNames()).contains("LookupPubKey");
		assertThat(historyId.elementNames()).doesNotContain("LookupId");
	}
	
	
	@Test
	void getPublicKeyByInvalidID(BpmClient bpmClient) {
		BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
		
		String threemaId = "invalidID";
		receiverData recDatId = new receiverData();
		recDatId.setIdentifier(threemaId);
		recDatId.setType(LookupType.THREEMAID);
		
		ExecutionResult resultId = bpmClient.start().subProcess(callable).execute(recDatId);
		receiverData resultDataId = resultId.data().last();
		History historyId = resultId.history();
			
		assertThat(resultDataId.getApiResponse()).isEqualTo("404");
		assertThat(historyId.elementNames()).contains("call(receiverData)");
		assertThat(historyId.elementNames()).contains("LookupPubKey");
		assertThat(historyId.elementNames()).doesNotContain("LookupId");
	}

}
