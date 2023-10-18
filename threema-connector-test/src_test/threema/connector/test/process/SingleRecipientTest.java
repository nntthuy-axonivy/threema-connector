package threema.connector.test.process;



import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import threema.connector.receiverData;

@IvyProcessTest
public class SingleRecipientTest {

	private static final BpmProcess SINGLE_RECEIVER_PROCESS = BpmProcess.name("singleRecipient");
	private static final String VALID_ID = "ECHOECHO";
	private static final String INVALID_EMAIL = "invalid@mail.ch";
	private static final String INVALID_PHONE = "41000000000";
	private static final String MESSAGE = "Hello World";
	
	@Test
	void sendMessageToValidSingleRecipientById(BpmClient bpmClient) {
		BpmElement callable = SINGLE_RECEIVER_PROCESS.elementName("call(String,String,LookupType)");
		ExecutionResult result = bpmClient.start().subProcess(callable).execute(MESSAGE, VALID_ID, util.LookupType.THREEMAID);
		receiverData resultData = result.data().last();
		String apiStatus = resultData.getApiResponse();
		
		assertThat(apiStatus).contains("200");
	}
	
	@Test
	void sendMessageToInvalidSingleRecipientByEmail(BpmClient bpmClient) {
		BpmElement callable = SINGLE_RECEIVER_PROCESS.elementName("call(String,String,LookupType)");
		ExecutionResult result = bpmClient.start().subProcess(callable).execute(MESSAGE, INVALID_EMAIL, util.LookupType.EMAIL);
		receiverData resultData = result.data().last();
		String apiStatus = resultData.getApiResponse();
		
		assertThat(apiStatus).contains("404");
	}
	
	@Test
	void sendMessageToInvalidSingleRecipientByPhone(BpmClient bpmClient) {
		BpmElement callable = SINGLE_RECEIVER_PROCESS.elementName("call(String,String,LookupType)");
		ExecutionResult result = bpmClient.start().subProcess(callable).execute(MESSAGE, INVALID_PHONE, util.LookupType.PHONE);
		receiverData resultData = result.data().last();
		String apiStatus = resultData.getApiResponse();
		
		assertThat(apiStatus).contains("404");
	}
	
}
