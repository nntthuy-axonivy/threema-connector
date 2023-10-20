package util;

import javax.xml.bind.DatatypeConverter;
import ch.ivyteam.ivy.environment.Ivy;
import ch.threema.apitool.CryptTool;
import ch.threema.apitool.results.EncryptResult;



public class MessageEncryptor {
  public record EncryptionResult(String encryptedMessage, String nonce) {};
  public static EncryptionResult encrypt(String publicKey, String msg) {

    byte[] encPrivKey = DatatypeConverter.parseHexBinary(Ivy.var().get("threemaConnector.privateKey"));
    byte[] encPubKey = DatatypeConverter.parseHexBinary(publicKey);

    EncryptResult result = CryptTool.encryptTextMessage(msg, encPrivKey, encPubKey);

    String encryptedMessage = DatatypeConverter.printHexBinary(result.getResult());
    String nonce = DatatypeConverter.printHexBinary(result.getNonce());
    return new EncryptionResult(encryptedMessage, nonce);
  }

}
