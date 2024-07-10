package com.kiwiqa.testngdemoqa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;

import com.kiwiqa.utils.UtilFile;

public class DemoQAVerifyFormPage extends UtilFile{

	public DemoQAVerifyFormPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="example-modal-sizes-title-lg") WebElement waittext;
	public void waitfortext() {
		Assert.assertEquals(waittext.getText(),"Thanks for submitting the form","Form not submitted...");
		System.out.println("Form filled.....");

	}
	public int verifyip(String path) throws Exception {
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line;
		int testcase =0; 
		List<WebElement> optfield = driver.findElements(By.xpath("//tbody//td//following-sibling::td"));
		for(WebElement field : optfield) {
			line = reader.readLine();
			if(line.equals(field.getText())) {
				continue;
			}
			else {
				System.out.println("not matched at "+line);
				testcase++;
			}
		}
		reader.close();
		return testcase;
	}
}

