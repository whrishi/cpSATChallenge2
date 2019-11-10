package pages;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Browser {
	protected WebDriver driver;

	protected WebDriver setup() {
		setDriver(driver);
		return driver;
	}

	protected WebDriver getDriver() {
		return driver;
	}

	protected void setDriver(WebDriver driver) {
		// Chrome version 77.0.*
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		// Disabling Push Notification popup
		capabilities.setCapability("notification.feature.enabled", false);
		driver = new ChromeDriver();
		this.driver = driver;
		getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		getDriver().manage().window().maximize();
	}

	protected WebElement findElement(By elementIdentifier) {
		return driver.findElement(elementIdentifier);
	}

	protected List<WebElement> findElements(By elementIdentifier) {
		return driver.findElements(elementIdentifier);
	}

	protected void get(String baseUrl) {
		driver.get(baseUrl);
	}

	protected String getTitle() {
		return driver.getTitle();
	}

	protected void quit() {
		this.driver.quit();

	}

}
