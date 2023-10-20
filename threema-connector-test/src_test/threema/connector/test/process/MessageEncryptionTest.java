package threema.connector.test.process;

import static javax.xml.bind.DatatypeConverter.parseHexBinary;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.Ivy;
import ch.threema.apitool.CryptTool;
import threema.connector.ReceiverData;

@IvyProcessTest
public class MessageEncryptionTest {

  private final static BpmProcess ENCRYPTION_PROCESS = BpmProcess.name("messageEncryption");
 /**
  * Dear Bug Hunter,
  * This credential is intentionally included for educational purposes only and does not provide access to any production systems.
  * Please do not submit it as part of our bug bounty program.
  */
  private final static String PUBLIC_KEY = "ffbb40cfced42f75c4d83c7d35300c0698bf3ef1ab49ace323a1bbc38ee23f36";
  private final static String PRIVATE_KEY = "ff364c727068fd6e3e6a711918393fa37649d902402a8eb31af108e79f625d82";

  @Test
  void encryptMessage(BpmClient bpmClient) {
    BpmElement callable = ENCRYPTION_PROCESS.elementName("call(receiverData)");
    // set PrivateKey for this test
    Ivy.var().set("threemaConnector.privateKey", PRIVATE_KEY);
    String plainMsg = "Hello World";
    ReceiverData recData = new ReceiverData();
    recData.setPlainMessage(plainMsg);
    recData.setPublicKey(PUBLIC_KEY);
    ExecutionResult result = bpmClient.start().subProcess(callable).execute(recData);
    ReceiverData resultData = result.data().last();
    assertThat(resultData.getEncryptedMessage()).isNotEmpty();
    byte[] decryptedByte = CryptTool.decrypt(
            parseHexBinary(resultData.getEncryptedMessage()),
            parseHexBinary(PRIVATE_KEY),
            parseHexBinary(PUBLIC_KEY),
            parseHexBinary(resultData.getNonce()));
    String decryptedMsg = new String(decryptedByte);
    assertThat(decryptedMsg).contains(plainMsg);
  }
}
