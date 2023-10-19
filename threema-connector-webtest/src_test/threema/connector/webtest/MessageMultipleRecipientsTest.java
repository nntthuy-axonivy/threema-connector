package threema.connector.webtest;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.selected;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.axonivy.ivy.webtest.IvyWebTest;
import com.axonivy.ivy.webtest.engine.EngineUrl;

@IvyWebTest(headless = false)
public class MessageMultipleRecipientsTest {
	
	@Test
	public void sendMessage() {
		open(EngineUrl.createProcessUrl("threema-connector-demo/18B1ED116183D822/start.ivp"));
		
		String message = "Hello World";
		String recipients = "validId\ninvalidId";
		
		// Assert empty form
		$(By.id("form:sendDemoMessageDataPlainMessage")).shouldBe(empty);
		$(By.id("form:sendDemoMessageDataReceiver")).shouldBe(empty);
		
		// Fill out form
		$(By.id("form:sendDemoMessageDataPlainMessage")).sendKeys(message);
		$(By.id("form:sendDemoMessageDataReceiver")).sendKeys(recipients);
		
		// Assert filled out form
		$(By.id("form:sendDemoMessageDataPlainMessage")).shouldBe(value(message));
		$(By.id("form:sendDemoMessageDataReceiver")).shouldBe(value(recipients));
				
		//$(By.id("form:proceed")).click();
	}

}
