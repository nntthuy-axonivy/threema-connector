package threema.connector.test.process;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.scripting.objects.List;
import threema.connector.receiverData;
import threema.connector.sendThreemaMessageData;
import util.LookupType;

@IvyProcessTest
public class MultipleRecipientsTest {

	private final static BpmProcess MULTIPLE_RECIPIENTS_PROCESS = BpmProcess.name("multipleRecipients");
	private final static String MESSAGE = "Hello World";
	
	
	@Test
	void prepareMultipleRecipients(BpmClient bpmClient) {
		List<String> recipients = new List<String>();
		recipients.add("invalidThreemaID");
		recipients.add("41000000000");
		recipients.add("invalid@email.ch");
		
		BpmElement callable = MULTIPLE_RECIPIENTS_PROCESS.elementName("call(String,List<String>)");	
		ExecutionResult result = bpmClient.start().subProcess(callable).execute(MESSAGE, recipients);
		sendThreemaMessageData msgData = result.data().last();
		List<receiverData> listReceiver = msgData.getReceiverData();
		
		assertThat(listReceiver.size()).isEqualTo(recipients.size());
		assertThat(listReceiver.get(0).getType()).isEqualTo(LookupType.THREEMAID);
		assertThat(listReceiver.get(1).getType()).isEqualTo(LookupType.PHONE);
		assertThat(listReceiver.get(2).getType()).isEqualTo(LookupType.EMAIL);
		
		for(String status : msgData.getApiResponses()) {
			assertThat(status).contains("404");
		}

	}
	
}
