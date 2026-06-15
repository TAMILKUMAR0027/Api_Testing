package com.tests;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class SeleniumTest {
  @Test
  public void openReqResSite() {
	  WebDriver d = new ChromeDriver();
	  d.manage().window().maximize();
	  try {
		  d.get("https://jsonplaceholder.typicode.com/");
		  String currentUrl=d.getCurrentUrl();
		  System.out.println(currentUrl);
		  assertTrue(currentUrl.contains("jsonplaceholder"));
	  }finally {
		d.quit();
	}
  }
}
