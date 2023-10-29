package driver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverTest {

	public static WebDriver webDriver;

	@Before
	public void setUp() throws Exception {
		webDriver = DriverFactory.getDriver();
	}

	@After
	public void tearDown() throws Exception {
		webDriver.quit();
	}

	public void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}

	public WebElement expandRootElement(WebElement element, WebDriver driver) {
		WebElement ele = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot",
				element);
		return ele;
	}

	@Test
	public void test() throws Exception {
		webDriver.get("https://www.bahn.de/p/view/meinebahn/login.shtml");
		assertThat(webDriver.getTitle()).contains("Meine Bahn");
		WebElement benutzername = webDriver.findElement(By.id("Benutzername"));
		benutzername.sendKeys("<your user>");
		WebElement passwort = webDriver.findElement(By.id("Passwort"));
		passwort.sendKeys("******");
		WebElement login = webDriver.findElement(By.cssSelector(".btn"));
		login.sendKeys(Keys.ENTER);
		Thread.sleep(20000);
		waitForLoad(webDriver);
		Thread.sleep(10000);
	}

}
