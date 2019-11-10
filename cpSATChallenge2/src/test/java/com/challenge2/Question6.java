package com.challenge2;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Question6 {
	private WebDriver driver;
	private String baseUrl;
	private String sUserDirectory = System.getProperty("user.dir");
	private String sTopGainersDataPath = sUserDirectory + "\\screenshots\\TopGainers.xls";
	private String sTopLoosersDataPath = sUserDirectory + "\\screenshots\\TopLoosers.xls";

	@BeforeTest
	public void beforeTest() {
		baseUrl = "https://www.nseindia.com/";

		// Chrome version 77.0.*
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		// DesiredCapabilities capabilities = new DesiredCapabilities();
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
		Actions action = new Actions(driver);

		WebElement elementLiveMarket = driver.findElement(By.xpath("//*[@id=\"main_livemkt\"]"));

		action.moveToElement(elementLiveMarket).build().perform();
		WebElement elementTopTenGainersLosers = driver.findElement(By.xpath("//*[@id=\"main_liveany_ttg\"]/a"));
		elementTopTenGainersLosers.click();
		// *[@id="tab7"]

		ArrayList data = new ArrayList();
        ArrayList headers = new ArrayList();
       

		WebElement elementTopGainersTable = driver.findElement(By.xpath("//*[@id=\"topGainers\"]/tbody"));
		// 2. Read a web table row count
		int rowCount = elementTopGainersTable.findElements(By.xpath("./tr")).size();
		System.out.println(rowCount);
		// 3. Read column header count
		int headerColumnCount = elementTopGainersTable.findElements(By.xpath("./tr/th")).size();
		//
		System.out.println(headerColumnCount);
		
		 // 4. Iterate through column header and collect all column headers into
        // an arraylist.
        for (int i = 1; i <= headerColumnCount; i++) {
            WebElement headerElement = elementTopGainersTable.findElement(By
                    .xpath("./tr[1]/th[" + i
                            + "]"));
            String header = headerElement.getText();
            System.out.println(header);
            headers.add(header);
        }
        
        // 5. Iterate through all the other rows for each column and collect all
        // data into an arraylist.
        for (int row = 2; row <= rowCount; row++) {
            for (int col = 1; col <= headerColumnCount; col++) {
                WebElement headerElement = elementTopGainersTable.findElement(By
                        .xpath("./tr[" + row
                                + "]/td[" + col + "]"));
                String cellData = headerElement.getText();
                System.out.println(cellData);
                data.add(cellData);
            }
        }
        
        // 6. Write all the data into an excel file
     		File exportToExcel = new File(sTopGainersDataPath);
     		try {
				writeToExcel("Train details", headers, data, exportToExcel);
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		/*
		// 4. Iterate through all the rows for each column and collect all data into an
		// arraylist.
		WebElement elementTableData;
		for (int row = 1; row <= rowCount; row++) {
			for (int col = 1; col <= headerColumnCount; col++) {
				if (row == 1) {
					elementTableData = elementTopGainersTable
							.findElement(By.xpath("./tr[" + row + "]/th[" + col + "]"));
					String header = elementTableData.getText();
					System.out.println(header);					
					data.add(header);
				} else {
					elementTableData = elementTopGainersTable
							.findElement(By.xpath("./tr[" + row + "]/td[" + col + "]"));
					String cellData = elementTableData.getText();
					System.out.println(cellData);
					data.add(cellData);
				}
			}
		}

		*/
		WebElement elementTopLosers = driver.findElement(By.xpath("//*[@id=\"tab8\"]"));
		elementTopLosers.click();
		
		WebElement elementTopLosersTable = driver.findElement(By.xpath("//*[@id=\"topLosers\"]/tbody"));
		// 2. Read a web table row count
		rowCount = elementTopLosersTable.findElements(By.xpath("./tr")).size();
		System.out.println(rowCount);
		// 3. Read column header count
		headerColumnCount = elementTopLosersTable.findElements(By.xpath("./tr/th")).size();
		//
		System.out.println(headerColumnCount);
		
		 // 4. Iterate through column header and collect all column headers into
        // an arraylist.
        for (int i = 1; i <= headerColumnCount; i++) {
            WebElement headerElement = elementTopLosersTable.findElement(By
                    .xpath("./tr[1]/th[" + i
                            + "]"));
            String header = headerElement.getText();
            System.out.println(header);
            headers.add(header);
        }
        
        // 5. Iterate through all the other rows for each column and collect all
        // data into an arraylist.
        for (int row = 2; row <= rowCount; row++) {
            for (int col = 1; col <= headerColumnCount; col++) {
                WebElement headerElement = elementTopLosersTable.findElement(By
                        .xpath("./tr[" + row
                                + "]/td[" + col + "]"));
                String cellData = headerElement.getText();              
                System.out.println(cellData);
                data.add(cellData);
            }
        }
        
        // 6. Write all the data into an excel file
     		exportToExcel = new File(sTopLoosersDataPath);
     		try {
				writeToExcel("Train details", headers, data, exportToExcel);
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
        /*
		
		// 4. Iterate through all the rows for each column and collect all data into an
		// arraylist.
		WebElement elementTableData2;
		for (int row = 1; row <= rowCount; row++) {
			for (int col = 1; col <= headerColumnCount; col++) {
				if (row == 1) {
					elementTableData2 = elementTopLosersTable
							.findElement(By.xpath("./tr[" + row + "]/th[" + col + "]"));
					String header = elementTableData2.getText();
					System.out.println(header);					
					data.add(header);
				} else {
					elementTableData2 = elementTopLosersTable
							.findElement(By.xpath("./tr[" + row + "]/td[" + col + "]"));
					String cellData = elementTableData2.getText();
					System.out.println(cellData);
					data.add(cellData);
				}
			}
		}
		*/
		


		System.out.println("f) Extract the date and time when top gainers and top losers data was taken from\n"
				+ "the NSE website and compare with the system time");
		WebElement elementCurrentDateTime = driver.findElement(By.xpath("//*[@id=\"dataTime\"]"));
		String sExpectedDateTime = elementCurrentDateTime.getText();
		System.out.println(sExpectedDateTime);		
		int iEndIndex = sExpectedDateTime.indexOf(":");
		sExpectedDateTime = sExpectedDateTime.substring(iEndIndex - 15, iEndIndex - 3);
		System.out.println(sExpectedDateTime);

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
		// SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String sActualDateTime = formatter.format(date);
		System.out.println(sActualDateTime);

		assertEquals(sActualDateTime, sExpectedDateTime);

	}	
	
	/*
	* This function will write data strored in an arraylist into the provided
	* excel sheet Usage : writeToExcel (String sheetName, ArraList header,
	* Arraylist data, File fileName); Return : None
	*/
	public static void writeToExcel(String sheetName, ArrayList headers,
	    ArrayList data, File outputFile) throws WriteException, IOException {
	// Creates a writable workbook with the given file name
	System.out.println("data size : " + data.size());
	System.out.println("headers.size() : " + headers.size());
	WritableWorkbook workbook = Workbook.createWorkbook(outputFile);
	WritableSheet sheet = workbook.createSheet(sheetName, 0);
	int rowIdx = 0;
	short colIdx = 0;

	// Create cell font and format
	WritableFont cellFont = new WritableFont(WritableFont.TIMES, 16);
	cellFont.setColour(Colour.BLACK);
	WritableCellFormat headerCellFormat = new WritableCellFormat(cellFont);
	headerCellFormat.setAlignment(Alignment.CENTRE);
	headerCellFormat.setBackground(Colour.ORANGE);
	headerCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

	Label lable = null;

	// Header
	for (Iterator cells = headers.iterator(); cells.hasNext();) {
	    lable = new Label(colIdx, rowIdx, (String) cells.next(),
	            headerCellFormat);
	    System.out.println(rowIdx + lable.getContents() + colIdx);
	    sheet.addCell(lable);
	    colIdx++;
	}

	// Data
	WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
	cellFormat.setAlignment(Alignment.LEFT);
	cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
	rowIdx = 1;
	colIdx = 0;
	for (Iterator rows = data.iterator(); rows.hasNext();) {
	    System.out.println("rowIdx : " + rowIdx);
	    System.out.println("colIdx : " + colIdx);
	    lable = new Label(colIdx, rowIdx, (String) rows.next(), cellFormat);
	    System.out.println("cell data: " + lable.getString());
	    sheet.addCell(lable);
	    colIdx++;
	    if (colIdx > headers.size() - 1) {
	        rowIdx++;
	        colIdx = 0;
	    }

	}
	// Writes out the data held in this workbook in Excel format
	workbook.write();
	// Close and free allocated memory
	workbook.close();
	}
	
}





