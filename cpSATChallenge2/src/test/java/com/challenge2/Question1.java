package com.challenge2;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

/*
1. Using Junit and WebDriver script, Go To http://agiletestingalliance.org/ in Google
Chrome and do the below.
a. Click on the certification’s menu item
b. Count the number of certification icons visible on the page - colour icons as per
the below image. Total 12. Print the URL every image is pointing to.
c. Confirm if the URL’s are working or not. If the URL is broken highlight that in a
soft Assert
 d. Take a screenshot
 e. Hover on CP-CCT
 f. Take a screenshot after hovering such that it shows the CP-CCT on the stored
screenshot image.
*/
public class Question1 {

	private WebDriver driver;
	private String baseUrl;
	private static SoftAssert softAssertions;
	// private JUnitSoftAssertions softly = new JUnitSoftAssertions();

	@Before
	public void setUp() throws Exception {
		softAssertions = new SoftAssert();
		baseUrl = "http://agiletestingalliance.org/";
		// Chrome version 77.0.*
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		// Disabling Push Notification popup
		//capabilities.setCapability("notification.feature.enabled", false);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		System.out.println("Open " + baseUrl + " , in Google Chrome");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		softAssertions.assertAll();
	}

	@Test
	public void testATACertifications() throws Exception {
		driver.get(baseUrl);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement eleCertificationLink = driver.findElement(By.xpath("/html/body/header/div/div/nav/ul/li[6]/a"));
		System.out.println("Click on the certification’s menu item");
		eleCertificationLink.click();
		System.out.println("Count the number of certification icons visible on the page");
		List<WebElement> eleCertificationMapArea = driver
				.findElements(By.xpath("//*[@id=\"content\"]/div/div[2]/map/area"));
		System.out.println("Number of certification icons visible on the page is : " + eleCertificationMapArea.size());
		// takeSnapShotOfElement(driver,
		// ".//certificationsList.png",eleCertificationMap);
		String strURL = "";
		String strURL2 = "";
		String strCTC = "CP-CCT";
		WebElement eleCTC = null;
		System.out.println("Print the URL every image is pointing to");
		for (WebElement eleCertification : eleCertificationMapArea) {
			// eleCertification.findElement(By.xpath("./area"));
			strURL = eleCertification.getAttribute("href");
			// *[@id="content"]/div/div[2]/map/area[1]
			System.out.println(strURL);
			if (eleCertification.getAttribute("title").equalsIgnoreCase(strCTC)) {
				eleCTC = eleCertification;
				strURL2 = eleCertification.getAttribute("href");
				System.out.println(eleCertification.getLocation());
			}			
		}
		System.out.println(
				"Confirm if the URL’s are working or not. If the URL is broken highlight that in a soft Assert");
		findBrokenLink(eleCertificationMapArea);
		System.out.println("Take a screenshot");
		takeSnapShot(driver, ".//certifications.png");
		System.out.println("Hover on CP-CCT");
		System.out.println(strURL2);
		WebElement target = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/map/area[4]"));
		Actions a1 = new Actions(driver);
		//a1.moveToElement(eleCTC,161,208).build().perform();
		//a1.moveToElement(target).build().perform();
		//a1.moveByOffset(161, 208);
		//((JavascriptExecutor)driver).executeScript("var mouseEvent = document.createEvent('MouseEvents');mouseEvent.initEvent('mouseover', true, true); arguments[0].dispatchEvent(mouseEvent);", target);
/*		String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";


((JavascriptExecutor)driver).executeScript(javaScript, target);
*/
		a1.moveByOffset(161, 208).build().perform();
//a1.moveToElement(eleCTC,116,234).build().perform();

		Thread.sleep(2000);
		
		System.out.println("Take a screenshot after hovering such that it shows the CP-CCT on the stored\r\n" + 
				"screenshot image.");
		takeSnapShot(driver, ".//cp-cct.png");		
	}

	/*
	This function will take screenshot	
	@param webdriver
	@param fileWithPath
	@throws Exception
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

	/*
	This function will take screenshot	
	@param webdriver
	@param fileWithPath
	@throws Exception
	*/
	public static void takeSnapShotOfElement(WebDriver webdriver, String fileWithPath, WebElement element) {

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

	/*
	 * This function will find broken link
	 * 
	 * @param eleWebURL
	 * 
	 */
	public static void findBrokenLink(List<WebElement> eleWebURL) {
		String url = "";
		HttpURLConnection huc = null;
		int respCode = 200;

		Iterator<WebElement> it = eleWebURL.iterator();

		while (it.hasNext()) {
			url = it.next().getAttribute("href");
			if (url == null || url.isEmpty()) {
				System.out.println("URL is either not configured for anchor tag or it is empty");
				continue;
			}

			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());
				huc.setRequestMethod("HEAD");
				huc.connect();
				respCode = huc.getResponseCode();
				if (respCode >= 400) {
					//System.out.println(url + " is a broken link");
					softAssertions.assertTrue(false, url + " is a broken link");
				}
				/*
				 * else { System.out.println(url + " is a valid link"); }
				 */

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * This function will find broken link
	 * 
	 * @param strWebURL
	 * 
	 */
	public static void findBrokenLink(String strWebURL) {

		HttpURLConnection huc = null;
		int respCode = 200;

		if (strWebURL == null || strWebURL.isEmpty()) {
			System.out.println("URL is either not configured for anchor tag or it is empty");
		}

		try {
			huc = (HttpURLConnection) (new URL(strWebURL).openConnection());
			huc.setRequestMethod("HEAD");
			huc.connect();
			respCode = huc.getResponseCode();
			if (respCode >= 400) {
				//System.out.println(strWebURL + " is a broken link");
				softAssertions.assertTrue(false, strWebURL + " is a broken link");
			}
			/*
			 * else { System.out.println(strWebURL + " is a valid link"); }
			 */

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
