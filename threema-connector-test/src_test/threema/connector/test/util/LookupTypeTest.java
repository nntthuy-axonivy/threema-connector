package threema.connector.test.util;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import util.LookupType;

public class LookupTypeTest {

  @Test
  void getTypeByString() {
    String email = "email";
    String phone = "phone";
    String threemaId = "threemaId";
    String invalid1 = "invalidType1";
    String invalid2 = "invalidType2";
    LookupType typeEmail = LookupType.getByString(email);
    assertThat(typeEmail).isEqualTo(LookupType.EMAIL);
    LookupType typePhone = LookupType.getByString(phone);
    assertThat(typePhone).isEqualTo(LookupType.PHONE);
    LookupType typeThreemaId = LookupType.getByString(threemaId);
    assertThat(typeThreemaId).isEqualTo(LookupType.THREEMAID);
    LookupType typeInvalid1 = LookupType.getByString(invalid1);
    assertThat(typeInvalid1).isEqualTo(LookupType.INVALID);
    LookupType typeInvalid2 = LookupType.getByString(invalid2);
    assertThat(typeInvalid2).isEqualTo(LookupType.INVALID);
  }

  @Test
  void getTypebyPattern() {
    String email = "xyz@xyz.yz";
    String phoneSimple = "00000000000";
    String phoneComplete = "+00000000000";
    String phoneCompleteSpace = "+00 00 000 00 00";
    String threemaId = "didegiasdfl";
    LookupType typeEmail = LookupType.getByPattern(email);
    assertThat(typeEmail).isEqualTo(LookupType.EMAIL);
    LookupType typePhone = LookupType.getByPattern(phoneSimple);
    assertThat(typePhone).isEqualTo(LookupType.PHONE);
    typePhone = LookupType.getByPattern(phoneComplete);
    assertThat(typePhone).isEqualTo(LookupType.PHONE);
    typePhone = LookupType.getByPattern(phoneCompleteSpace);
    assertThat(typePhone).isEqualTo(LookupType.PHONE);
    LookupType typeThreemaId = LookupType.getByPattern(threemaId);
    assertThat(typeThreemaId).isEqualTo(LookupType.THREEMAID);
  }
}
