package com.challenge2;

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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Question4 {
	
	private WebDriver driver;
	private String baseUrl;
	private String sUserDirectory = System.getProperty("user.dir");
	private String dataFilePath = sUserDirectory + "\\src\\test\\resources\\SearchEquity.xls";
	private String sScreenShotPath = sUserDirectory + "\\screenshots\\";

	@BeforeTest
	public void beforeTest() {
		baseUrl = "https://www.nseindia.com/";
		// Chrome version 77.0.*
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		//DesiredCapabilities capabilities = new DesiredCapabilities();
		// Disabling Push Notification popup
		//capabilities.setCapability("notification.feature.enabled", false);
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
	public void testExcelDataHandling() throws InterruptedException {
		// public void testExcelDataHandling(String sEquitySearch) {
		// String sEquitySearch = "";
		driver.get(baseUrl);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		String[] aSearchData = getTableArray(dataFilePath, "TestData", "SearchEquity");	
		
		
		for (String sEquitySearch : aSearchData) {
			WebElement eleEquitySearchKeyword = driver.findElement(By.xpath("//*[@id=\"keyword\"]"));
			//System.out.println(sEquitySearch);
			System.out.println("1. Enter the company name \"" + sEquitySearch + "\"");
			eleEquitySearchKeyword.sendKeys(sEquitySearch);
			
			wait.until(
					ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"ajax_response\"]/ol/li"))));
			System.out.println("2. Click on the magnifying glass or hit enter");
			driver.findElement(By.xpath("//*[@id=\"keyword\"]")).sendKeys(Keys.RETURN);
			
			System.out.println("3. A new page opens up with the details of the company");

			System.out.println("4. Fetch and Print the following on the console\n" 
					+ " 1. Face Value\n"
					+ " 2. 52 week high\n" 
					+ " 3. 52 week low");

			WebElement eleTradeSnapshot = driver.findElement(By.xpath("//*[@id=\"fundamentals_tbl\"]"));

			WebElement eleFaceValue = eleTradeSnapshot.findElement(By.xpath("//*[@id=\"face\"]/a"));
			System.out.print(eleFaceValue.getText() + " : ");
			System.out.println(eleFaceValue.findElement(By.xpath("//*[@id=\"faceValue\"]")).getText());

			WebElement eleYearHigh = eleTradeSnapshot.findElement(By.xpath("./ul/li[8]/a"));
			System.out.print(eleYearHigh.getText() + " : ");
			System.out.println(eleYearHigh.findElement(By.xpath("//*[@id=\"high52\"]")).getText());
			System.err.println(eleYearHigh.findElement(By.xpath("//*[@id=\"mock_cm_adj_high_dt\"]")).getText());

			WebElement eleYearLow = eleTradeSnapshot.findElement(By.xpath("./ul/li[9]/a"));
			System.out.print(eleYearLow.getText() + " : ");
			System.out.println(eleYearLow.findElement(By.xpath("//*[@id=\"low52\"]")).getText());
			System.err.println(eleYearLow.findElement(By.xpath("//*[@id=\"mock_cm_adj_low_dt\"]")).getText());

			System.out.println("5. Take screen shot of the searched equity \"" + sEquitySearch + "\"");
			takeSnapShot(driver, sScreenShotPath + sEquitySearch + ".png");
			System.out.println("=================================================================");

		}
	}

	@DataProvider(name = "testData1")
	public Object[] createData1() throws Exception {
		Object[] retObjArr = getTableArray(dataFilePath, "TestData", "SearchEquity");
		System.out.println("*****************  1 *************************");
		return (retObjArr);
	}

	@DataProvider(name = "testData2")
	public Object[] createData2() throws Exception {
		Object[] retObjArr = getTableArray(dataFilePath, "TestData", "SearchEquity");
		System.out.println("*****************  2 *************************");
		return (retObjArr);
	}

	@DataProvider(name = "testData3")
	public Object[] createData3() throws Exception {
		Object[] retObjArr = getTableArray(dataFilePath, "TestData", "SearchEquity");
		System.out.println("*****************  3 *************************");
		return (retObjArr);
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

	
	public String[] getTableArray(String xlFilePath, String sheetName, String tableName) {
		String[] tabArray = null;
		try {
			//System.out.println(xlFilePath);
			Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
			Sheet sheet = workbook.getSheet(sheetName);

			int startRow, ci;
			//, startCol, cj;

			//int totalNoOfCols = sheet.getColumns();
			int totalNoOfRows = sheet.getRows();
			//System.out.println(totalNoOfCols);
			//System.out.println(totalNoOfRows);

			Cell tableStart = sheet.findCell(tableName);
			startRow = tableStart.getRow();
			//startCol = tableStart.getColumn();

			//System.out.println("startRow=" + startRow + ", " + "startCol=" + startCol);
			tabArray = new String[totalNoOfRows - 1];
			ci = 0;

			for (int i = startRow + 1, j = 0; i < totalNoOfRows; i++, ci++) {
				tabArray[ci] = sheet.getCell(j, i).getContents().trim();
				//System.out.println(tabArray[ci]);
			}
		} catch (Exception e) {
			System.out.println("error in getTableArray()");

		}

		return (tabArray);
	}

}
