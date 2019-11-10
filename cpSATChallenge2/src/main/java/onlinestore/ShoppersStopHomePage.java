package onlinestore;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.Browser;

public class ShoppersStopHomePage extends Browser {
	protected WebDriver shoppersStopDriver;
	String baseUrl = "https://www.shoppersstop.com/";
	
	String sRoller_xpath  = "//*[@role='presentation']";
    String sNextArrow_xpath = "/html/body/main/div[18]/div/div[3]";
    
	
    String allStore_xpath = "//*[@id=\"header-info-bar\"]/div/div/div[2]/ul/li[1]/a";
	String stores_xpath = "//*[@id=\"city-name\"]/option";
	
	String men_xpath = "/html/body/main/nav/div[2]/div/ul/li[4]/a";
	String mensFragrance_xpath = "/html/body/main/nav/div[2]/div/ul/li[4]/div/div/ul/li[6]";
	
	String all_mens_fragrance_xpath =  "/html/body/main/nav/div[2]/div/ul/li[4]/div/div/ul/li[6]/div/ul/li[1]/div/ul/li";
	
	protected By getsRoller_xpath() {
		return By.xpath(sRoller_xpath);
	}
	
	protected By getsNextArrow_xpath() {
		return By.xpath(sNextArrow_xpath);
	}

	protected By getMen_xpath() {
		return By.xpath(men_xpath);
	}

	protected By getMens_fragrance_xpath() {
		return By.xpath(mensFragrance_xpath);
	}

	protected By getAll_mens_fragrance_xpath() {
		return By.xpath(all_mens_fragrance_xpath);
	}
	
	protected By get_allStore_xpath() {
		By allStore = By.xpath(allStore_xpath);
		return allStore;
	}
	
	protected By get_stores_xpath() {		
		return By.xpath(stores_xpath);
	}
		
	public WebDriver standard_setup() {
		shoppersStopDriver = setup();
		System.out.println("Open " + baseUrl + " , in Google Chrome");
		return shoppersStopDriver;
	}
	
	public void standard_teardown() {
		shoppersStopDriver.quit();
	}
	
	
    public void goToShoppersStop() {
    	shoppersStopDriver.get(baseUrl);    	
	}
    
    //Get the title of Page
    public String getShoppersStopTitle() {
    	return shoppersStopDriver.getTitle();
    }
    
    //Click on All Store
    public void clickAllStoreLink() {
    	shoppersStopDriver.findElement(get_allStore_xpath()).click();    	
    	WebDriverWait wait = new WebDriverWait(driver, 10);
    	wait.until(ExpectedConditions.visibilityOfElementLocated((getMens_fragrance_xpath())));
    }    
    
    // Returns list of WebElements
    public List<WebElement> getStoresInYourCity() {
    	return shoppersStopDriver.findElements(get_stores_xpath());
    }
    
    public void printCity() {
    	List<WebElement> eleStoreCity =  getStoresInYourCity();
    	for (WebElement element : eleStoreCity ) {
    		System.out.println(element.getText());
    	}
    }
    
    public void verifyCities(String[] aExpectedCities) {
    	String[] aActualCities = new String[aExpectedCities.length];
    	List<WebElement> eleStoreCity =  getStoresInYourCity();
    	int index = 0;
    	for (WebElement element : eleStoreCity ) {    		
    		for (String sCity : aExpectedCities) {
    			if (sCity.equalsIgnoreCase(element.getText())) {    				
    				aActualCities[index] = element.getText();
    				System.out.println(aActualCities[index]);
    	    		index++;    	    		
    			}
    		}
    	}    	
    	assertEquals(aActualCities, aExpectedCities, "Fail");
    }
    
    public void viewAllBanner() {    	
    	System.out.println(getnumberOfBanners());
    	for (int i=0; i < getnumberOfBanners();i++) {
    		System.out.println("Clicking arrow");
    		clickNextArrowOnBanner();
    	}
    }
    
    public int getnumberOfBanners() {
    	return shoppersStopDriver.findElements(getsRoller_xpath()).size();
    }
    
    public void clickNextArrowOnBanner() {
    	shoppersStopDriver.findElement(getsNextArrow_xpath()).click();
    }
    
    public void listAccessoriesUnderMensFragrance() {
    	Actions actionHover = new Actions(driver);
    	
    	WebElement elementMen = shoppersStopDriver.findElement(getMen_xpath());
    	actionHover.moveToElement(elementMen).build().perform();
    	
    	//Creating the JavascriptExecutor interface object by Type casting		
        //JavascriptExecutor js = (JavascriptExecutor)driver;	    	
    	//WebElement elementMensFragrance = shoppersStopDriver.findElement(getMens_fragrance_xpath());
    	//js.executeScript("arguments[0].click();", elementMensFragrance);
    	////actionHover.moveToElement(elementMensFragrance).build().perform();
    	
    	WebDriverWait wait = new WebDriverWait(driver, 5);
    	wait.until(ExpectedConditions.elementToBeClickable(getMens_fragrance_xpath())).click();
    	
    	List<WebElement> listAccessories = shoppersStopDriver.findElements(getAll_mens_fragrance_xpath());
		for (WebElement element : listAccessories) {		
    		 System.out.println(element.getText());
    	}
    }

}
