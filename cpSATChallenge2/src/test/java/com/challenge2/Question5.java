package com.challenge2;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import onlinestore.ShoppersStopHomePage;

public class Question5 {

	private WebDriver q5Driver;
	ShoppersStopHomePage shoppersStop = new ShoppersStopHomePage();

	@BeforeTest
	public void beforeTest() {		
		q5Driver = shoppersStop.standard_setup();
	}

	@AfterTest
	public void afterTest() {
		shoppersStop.standard_teardown();
	}

	@Test
	public void testShopperStop() {
		shoppersStop.goToShoppersStop();

		System.out.println("1. Click on the banner slider > for the number of times till the banner gets repeated");
		shoppersStop.viewAllBanner();

		System.out.println("2. Print all the accessories name under MEN section > Men’s Fragrance");
		shoppersStop.listAccessoriesUnderMensFragrance();

		System.out.println("3. Click on All Stores link");
		shoppersStop.clickAllStoreLink();

		System.out.println("4. Print the Cities name that available in Find Stores in your city");
		shoppersStop.printCity();

		System.out.println("5. Assert Agra, Bhopal and Mysore are available in Find Stores in your city");
		String[] aExpectedCities = { "Agra", "Bhopal", "Mysore" };
		shoppersStop.verifyCities(aExpectedCities);
		String sHomePageTitle = shoppersStop.getShoppersStopTitle();

		System.out.println("6. Print the page title in console.");
		System.out.println(sHomePageTitle);
	}

}
