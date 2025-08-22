package threema.connector.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import static com.axonivy.utils.e2etest.enums.E2EEnvironment.REAL_SERVER;

import com.axonivy.utils.e2etest.utils.E2ETestUtils;

import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.threema.mocks.ThreemaServiceMock;
import threema.connector.test.constants.ThreemaTestConstants;

public class BaseSetup {
 /**
  * Dear Bug Hunter,
  * This credential is intentionally included for educational purposes only and does not provide access to any production systems.
  * Please do not submit it as part of our bug bounty program.
  */
  private static String MOCK_SECRET = "D7RTdRQY9Ew6e8Jb";
  private static String MOCK_PRIVATE_KEY = "15b7a803318da4ddc2dfb7eecce67d560e11c5ec9c883d6c7ad2903ddbbd56d2";
  protected boolean isRealTest;

  @BeforeEach
  void setup(ExtensionContext context, AppFixture fixture) {
    isRealTest = context.getDisplayName().equals(REAL_SERVER.getDisplayName());
    E2ETestUtils.determineConfigForContext(context.getDisplayName(), runRealEnv(fixture), runMockEnv(fixture));
  }

  private Runnable runRealEnv(AppFixture fixture) {
    return () -> {
      String threemaId = System.getProperty(ThreemaTestConstants.THREEMA_ID);
      String secret = System.getProperty(ThreemaTestConstants.SECRET);
      String privateKey = System.getProperty(ThreemaTestConstants.PRIVATE_KEY);

      fixture.var("threemaConnector.threemaId", threemaId);
      fixture.var("threemaConnector.secret", secret);
      fixture.var("threemaConnector.privateKey", privateKey);
    };
  }

  private Runnable runMockEnv(AppFixture fixture) {
    return () -> {
      fixture.config("RestClients.ThreemaGateway.Url", ThreemaServiceMock.URI);
      fixture.var("threemaConnector.secret", MOCK_SECRET);
      fixture.var("threemaConnector.privateKey", MOCK_PRIVATE_KEY);
    };
  }
}
