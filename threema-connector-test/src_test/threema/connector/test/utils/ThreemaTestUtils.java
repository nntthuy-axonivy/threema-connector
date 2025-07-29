package threema.connector.test.utils;

import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.threema.mocks.ThreemaServiceMock;
import threema.connector.test.constants.ThreemaTestConstants;

public class ThreemaTestUtils {

 /**
  * Dear Bug Hunter,
  * This credential is intentionally included for educational purposes only and does not provide access to any production systems.
  * Please do not submit it as part of our bug bounty program.
  */
  private static String MOCK_SECRET = "D7RTdRQY9Ew6e8Jb";
  private static String MOCK_PRIVATE_KEY = "15b7a803318da4ddc2dfb7eecce67d560e11c5ec9c883d6c7ad2903ddbbd56d2";

  public static void setUpConfigForContext(String contextName, AppFixture fixture) {
    switch (contextName) {
      case ThreemaTestConstants.REAL_CALL_CONTEXT_DISPLAY_NAME:
        setUpConfigForApiTest(fixture);
        break;
      case ThreemaTestConstants.MOCK_SERVER_CONTEXT_DISPLAY_NAME:
        setUpConfigForMockServer(fixture);
        break;
      default:
        break;
    }
  }

  private static void setUpConfigForApiTest(AppFixture fixture) {
    String threemaId = System.getProperty(ThreemaTestConstants.THREEMA_ID);
    String secret = System.getProperty(ThreemaTestConstants.SECRET);
    String privateKey = System.getProperty(ThreemaTestConstants.PRIVATE_KEY);

    fixture.var("threemaConnector.threemaId", threemaId);
    fixture.var("threemaConnector.secret", secret);
    fixture.var("threemaConnector.privateKey", privateKey);
  }

  public static void setUpConfigForMockServer(AppFixture fixture) {
    fixture.config("RestClients.ThreemaGateway.Url", ThreemaServiceMock.URI);
    fixture.var("threemaConnector.secret", MOCK_SECRET);
    fixture.var("threemaConnector.privateKey", MOCK_PRIVATE_KEY);
  }
}
