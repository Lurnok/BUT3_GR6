package com.iut.banque.test.selenium;

import java.io.IOException;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestWeb {
   private WebDriver driver;
   private static Process server;

   @Before
   public void setUp() throws IOException {
      WebDriverManager.chromedriver().setup();
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--no-sandbox");
      options.addArguments("--disable-dev-shm-usage");
      options.addArguments("--user-data-dir=/tmp/chrome-user-data-" + System.currentTimeMillis()); 
      driver = new ChromeDriver(options);
      driver.get("http://localhost:19000/_00_ASBank2023/");

   }

   @Test
   public void testImageExistsOnMainPage() {
      

      WebElement image = driver.findElement(By.tagName("img"));

      assertTrue("Image is not displayed on the main page", image.isDisplayed());
   }

   @After
   public void tearDown() {
      if (driver != null) {
         driver.quit(); // Close browser after test
      }
      if (server != null) {
         server.destroy();
         System.out.println("Server stopped.");
      }
   }
}
