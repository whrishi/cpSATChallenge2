package com.challenge2;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

/*
2. Using TestNG and WebDriver script, open https://www.nseindia.com/ in Google
Chrome and do the below
a. Using FindElements method of webdriver get the advances, Declines and
Unchanged numbers from the maket watch window - reference image
b. Print the Minimum number
e.g. Unchanged 0
*/
public class Question2 {
	private WebDriver driver;
	private String baseUrl;

	@BeforeTest
	public void beforeTest() {
		baseUrl = "https://www.nseindia.com/";
		// Chrome version 77.0.*
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		// Disabling Push Notification popup
		// capabilities.setCapability("notification.feature.enabled", false);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		System.out.println("Open " + baseUrl + " , in Google Chrome");
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	@Test
	public void testMarketWatch() {
		driver.get(baseUrl);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		System.out.println("Using FindElements method of webdriver get the advances, Declines and\n"
				+ "Unchanged numbers from the maket watch window");
		List<WebElement> eleMarketWatch = driver
				.findElements(By.xpath("//*[@id=\"container\"]/div[3]/div[2]/div[1]/div[2]/ul/li"));
		// System.out.println(eleMarketWatch.size());
		HashMap<String, Integer> hmMarketWatch = new HashMap<String, Integer>();
		String[] aAdvances;
		for (WebElement eleAdvance : eleMarketWatch) {
			// WebElement eleSpan = eleAdvance.findElement(By.xpath("./span"));
			//System.out.println(eleAdvance.getText());
			aAdvances = eleAdvance.getText().split("\\n");
			hmMarketWatch.put(aAdvances[0], Integer.parseInt(aAdvances[1]));
		}
		System.out.println("Print the Minimum number");

		hmMarketWatch = sortByValue(hmMarketWatch);

		// Print the Minimum number from sorted hashmap
		/*
		 * Map<String, Integer> mSortedMarketWatch = sortByValue(hmMarketWatch); for
		 * (Map.Entry<String, Integer> en : mSortedMarketWatch.entrySet()) {
		 * System.out.println("Key = " + en.getKey() + ", Value = " + en.getValue()); }
		 */
		System.out.println(hmMarketWatch.entrySet().iterator().next().getKey());
		System.out.println(hmMarketWatch.entrySet().iterator().next().getValue());
	}

	/*
	 * This function will sort the provided HashMap collection
	 * 
	 * @param hmToSort
	 * 
	 * @return hmSorted
	 * 
	 */
	public HashMap<String, Integer> sortByValue(HashMap<String, Integer> hmToSort) {
		// Create a list from elements of HashMap
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hmToSort.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> objectFirst, Map.Entry<String, Integer> objectSecond) {
				return (objectFirst.getValue()).compareTo(objectSecond.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<String, Integer> hmSorted = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> mapSorted : list) {
			hmSorted.put(mapSorted.getKey(), mapSorted.getValue());
		}
		return hmSorted;
	}

}
