package threema.connector.test.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import util.KeyPairGenerator;
import util.KeyPairGenerator.KeyPair;

public class KeyPairGeneratorTest {

  @Test
  void generateKeyPair() {
    KeyPair keys = KeyPairGenerator.generate();
    assertThat(keys.publicKey()).isNotEmpty();
    assertThat(keys.privateKey()).isNotEmpty();

    assertThat(keys.publicKey().length()).isEqualTo(64);
    assertThat(keys.privateKey().length()).isEqualTo(64);
  }

}
