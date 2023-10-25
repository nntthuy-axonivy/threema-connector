package util;

import javax.xml.bind.DatatypeConverter;

import ch.threema.apitool.CryptTool;

public class KeyPairGenerator {

  public record KeyPair(String publicKey, String privateKey) {}

  public static KeyPair generate() {
    byte[] publicKey = new byte[32];
    byte[] privateKey = new byte[32];

    CryptTool.generateKeyPair(publicKey, privateKey);
    return new KeyPair(DatatypeConverter.printHexBinary(publicKey), DatatypeConverter.printHexBinary(privateKey));
  }

}
