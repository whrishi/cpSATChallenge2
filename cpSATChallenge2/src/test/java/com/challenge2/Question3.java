package com.challenge2;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

/*
3. Using TestNG and WebDriver script, open https://www.nseindia.com/ in Firefox and
do the below
a. In the Equity window, reference image attached.
b. Enter the company name Eicher Motors Limited
c. Click on the magnifying glass or hit enter
d. A new page opens up with the details of the company
e. Take screen shot of the searched equity
f. Fetch and Print the following on the console
 1. Face Value
 2. 52 week high
 3. 52 week low
*/
public class Question3 {

	private WebDriver driver;
	private String baseUrl;
	private String sUserDirectory = System.getProperty("user.dir");
	private String sScreenShotPath = sUserDirectory + "\\screenshots\\";

	@BeforeTest
	public void beforeTest() {
		baseUrl = "https://www.nseindia.com/";
		// Firefox version 40
		System.setProperty("webdriver.gecko.driver", ".\\lib\\geckodriver.exe");
		FirefoxOptions capabilities = new FirefoxOptions();
		capabilities.setCapability("marionette", false);
		driver = new FirefoxDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		System.out.println("Open " + baseUrl + " , in FireFox");
	}

	@AfterTest
	public void afterTest() {
		// driver.quit();
	}

	@Test
	public void tsetEquityMarketeData() {
		driver.get(baseUrl);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		String sEquitySearch = "Eicher Motors Limited";
		WebElement eleEquitySearchKeyword = driver.findElement(By.xpath("//*[@id=\"keyword\"]"));
		System.out.println("1. Enter the company name \"" + sEquitySearch +"\"");
		eleEquitySearchKeyword.sendKeys(sEquitySearch);		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"ajax_response\"]/ol/li"))));
		System.out.println("2. Click on the magnifying glass or hit enter");
		driver.findElement(By.xpath("//*[@id=\"keyword\"]")).sendKeys(Keys.RETURN);
		System.out.println("3. A new page opens up with the details of the company");
		System.out.println("4. Take screen shot of the searched equity");
		takeSnapShot(driver, sScreenShotPath + sEquitySearch + ".png");
		System.out.println("5. Fetch and Print the following on the console\n" 
				+ " 1. Face Value\n" 
				+ " 2. 52 week high\n"
				+ " 3. 52 week low");

		WebElement eleTradeSnapshot = driver.findElement(By.xpath("//*[@id=\"fundamentals_tbl\"]"));

		WebElement eleFaceValue = eleTradeSnapshot.findElement(By.xpath("//*[@id=\"face\"]/a"));
		System.out.print(eleFaceValue.getText() + " ");
		System.out.println(eleFaceValue.findElement(By.xpath("//*[@id=\"faceValue\"]")).getText());

		WebElement eleYearHigh = eleTradeSnapshot.findElement(By.xpath("./ul/li[8]/a"));
		System.out.print(eleYearHigh.getText() + " ");
		System.out.println(eleYearHigh.findElement(By.xpath("//*[@id=\"high52\"]")).getText());
		System.err.println(eleYearHigh.findElement(By.xpath("//*[@id=\"mock_cm_adj_high_dt\"]")).getText());

		WebElement eleYearLow = eleTradeSnapshot.findElement(By.xpath("./ul/li[9]/a"));
		System.out.print(eleYearLow.getText() + " ");
		System.out.println(eleYearLow.findElement(By.xpath("//*[@id=\"low52\"]")).getText());
		System.err.println(eleYearLow.findElement(By.xpath("//*[@id=\"mock_cm_adj_low_dt\"]")).getText());
	}

	/*
	 * This function will take screenshot
	 * 
	 * @param webdriver
	 * 
	 * @param fileWithPath
	 * 
	 * @throws Exception
	 */
	public static void takeSnapShot(WebDriver webdriver, String fileWithPath) {

		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

		// Call getScreenshotAs method to create image file
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		// Move image file to new destination
		File DestFile = new File(fileWithPath);

		// Copy file at destination
		try {
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
