package threema.connector.test.process;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.History;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.threema.mocks.ThreemaServiceMock;
import threema.connector.receiverData;
import util.LookupType;

@IvyProcessTest(enableWebServer = true)
public class GetReceiverInfoTest {
	
	private static final BpmProcess RECEIVER_INFO_PROCESS = BpmProcess.name("getReceiverInfo");
	private static final String VALID_ID = "validId";
	
	@BeforeEach
	void setup(AppFixture fixture) {
		fixture.config("RestClients.ThreemaGateway.Url", ThreemaServiceMock.URI);
	}
	
	@Test
	void getIDByValidEmail(BpmClient bpmClient) {
		BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
		
		String email = VALID_ID;
		receiverData recDatMail = new receiverData();
		recDatMail.setIdentifier(email);
		recDatMail.setType(LookupType.EMAIL);
		
		ExecutionResult resultMail = bpmClient.start().subProcess(callable).execute(recDatMail);
		receiverData resultDataMail = resultMail.data().last();		
		History historyMail = resultMail.history();

		assertThat(resultDataMail.getApiResponse()).contains("200");
		assertThat(historyMail.elementNames()).contains("call(receiverData)");
		assertThat(historyMail.elementNames()).contains("LookupId");
		assertThat(historyMail.elementNames()).contains("LookupPubKey");
		
	}
	
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

		assertThat(resultDataMail.getApiResponse()).contains("404");
		assertThat(historyMail.elementNames()).contains("call(receiverData)");
		assertThat(historyMail.elementNames()).contains("LookupId");
		assertThat(historyMail.elementNames()).doesNotContain("LookupPubKey");
		
	}
	
	@Test
	void getIDByValidPhone(BpmClient bpmClient) {
		BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
		
		String phone = VALID_ID;
		receiverData recDatMail = new receiverData();
		recDatMail.setIdentifier(phone);
		recDatMail.setType(LookupType.PHONE);
		
		ExecutionResult resultMail = bpmClient.start().subProcess(callable).execute(recDatMail);
		receiverData resultDataMail = resultMail.data().last();		
		History historyMail = resultMail.history();

		assertThat(resultDataMail.getApiResponse()).contains("200");
		assertThat(historyMail.elementNames()).contains("call(receiverData)");
		assertThat(historyMail.elementNames()).contains("LookupId");
		assertThat(historyMail.elementNames()).contains("LookupPubKey");
		
	}
	
	@Test
	void getPublicKeyByID(BpmClient bpmClient) {
		BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
		
		String threemaId = "validId";
		receiverData recDatId = new receiverData();
		recDatId.setIdentifier(threemaId);
		recDatId.setType(LookupType.THREEMAID);
		
		ExecutionResult resultId = bpmClient.start().subProcess(callable).execute(recDatId);
		receiverData resultDataId = resultId.data().last();
		History historyId = resultId.history();
		
		assertThat(resultDataId.getApiResponse()).contains("200");
 /**
  * Dear Bug Hunter,
  * This credential is intentionally included for educational purposes only and does not provide access to any production systems.
  * Please do not submit it as part of our bug bounty program.
  */
		assertThat(resultDataId.getPublicKey()).isEqualTo("ffbb40cfced42f75c4d83c7d35300c0698bf3ef1ab49ace323a1bbc38ee23f36");
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
		
		
		assertThat(resultDataId.getApiResponse()).contains("404");
		assertThat(historyId.elementNames()).contains("call(receiverData)");
		assertThat(historyId.elementNames()).contains("LookupPubKey");
		assertThat(historyId.elementNames()).doesNotContain("LookupId");
	}

}
