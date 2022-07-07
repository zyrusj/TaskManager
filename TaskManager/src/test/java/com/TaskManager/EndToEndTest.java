package com.TaskManager;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeDriverService.Builder;


public class EndToEndTest {

	@Test
	public void test() {
		System.out.print("hello");

		assertEquals(1, 1);
	}
	@Test
	public void e2eTest() {
		// Set the ChromeDriver Exe Path
		System.out.print("hello");
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		WebDriver driver = new ChromeDriver();
		// Launch your browser
		driver.get("https://www.google.com/");
		System.out.print(driver.getTitle());
	}
//    @BeforeClass
//    public static void setUp(){
//        System.setProperty("webdriver.chrome.driver", "/selenium-driver/chromedriver");
//        driver = new ChromeDriver();
//    }
//
//    @Test
//    public void testChromeSelenium() {
//        driver.get("http://localhost:8080/");
//        
//    }

//    @AfterClass
//    public static void cleanUp(){
//        if (driver != null) {
//            driver.close();
//            driver.quit();
//        }
//    }
}
