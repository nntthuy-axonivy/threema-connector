package threema.connector.webtest;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.axonivy.ivy.webtest.IvyWebTest;
import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.axonivy.ivy.webtest.primeui.PrimeUi;


@IvyWebTest(headless = false)
public class MessageSingleRecipientTest {
	
	
	@Test
	public void sendMessage() {
		open(EngineUrl.createProcessUrl("threema-connector-demo/18B22F69680901D3/start.ivp"));
		
		String message = "Hello World";
		String validId = "validId";
		
		// Assert empty form
		$(By.id("form:sendDemoMessageDataPlainMessage")).shouldBe(empty);
		$(By.id("form:sendDemoMessageDataReceiver")).shouldBe(empty);
		$(By.id("form:typeSelection:2")).shouldNotBe(selected);
		
		// Fill out form
		$(By.id("form:sendDemoMessageDataPlainMessage")).sendKeys(message);
		$(By.id("form:sendDemoMessageDataReceiver")).sendKeys(validId);
		PrimeUi.selectOneRadio(By.id("form:typeSelection")).selectItemByValue("threemaid");
		
		// Assert filled out form
		$(By.id("form:sendDemoMessageDataPlainMessage")).shouldBe(value(message));
		$(By.id("form:sendDemoMessageDataReceiver")).shouldBe(value(validId));
		$(By.id("form:typeSelection:2")).shouldBe(selected);
				
		//$(By.id("form:proceed")).click();
	}

}
