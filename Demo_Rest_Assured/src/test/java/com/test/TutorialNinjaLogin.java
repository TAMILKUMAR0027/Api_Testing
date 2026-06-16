package com.test;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TutorialNinjaLogin {
  @Test
  public void validLogin() {
	  Response res=RestAssured.given().when().get("http://localhost:3001/0");
	  String email=res.jsonPath().getString("username");
	  String password=res.jsonPath().getString("password");
	  System.out.println(email+" "+password);
	  WebDriver d=new ChromeDriver();
	  d.manage().window().maximize();
	  d.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	  d.get("https://tutorialsninja.com/demo/");
	  d.findElement(By.xpath("//span[normalize-space()='My Account']")).click();
	  d.findElement(By.xpath("//a[normalize-space()='Login']")).click();
	  d.findElement(By.xpath("//input[@id='input-email']")).sendKeys(email);
	  d.findElement(By.xpath("//input[@id='input-password']")).sendKeys(password);
	  d.findElement(By.xpath("//input[@value='Login']")).click();
	  assertEquals("My Account", d.findElement(By.xpath("//h2[normalize-space()='My Account']")).getText());
	  d.quit();
  }
}
