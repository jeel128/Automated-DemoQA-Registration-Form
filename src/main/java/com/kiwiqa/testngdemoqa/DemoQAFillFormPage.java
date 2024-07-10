package com.kiwiqa.testngdemoqa;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.kiwiqa.utils.UtilFile;
public class DemoQAFillFormPage extends UtilFile{
	public DemoQAFillFormPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(id = "firstName") WebElement fname;
	@FindBy(id = "lastName") WebElement lname;
	@FindBy(id = "userEmail") WebElement uemail;
	@FindBy(id = "userNumber") WebElement unumber;
	@FindBy(id = "dateOfBirthInput") WebElement udob;
	@FindBy(id = "subjectsInput") WebElement usubinput;
	@FindBy(xpath = "//div[contains(@class,'multi-value__remove')]") WebElement usubwait;
	@FindBy(id = "uploadPicture") WebElement upic;
	@FindBy(id = "currentAddress") WebElement uaddress;
	@FindBy(xpath = "//div[@id='state']") WebElement ustateclick;
	@FindBy(xpath = "//div[@id='state']//input") WebElement ustateip;
	@FindBy(xpath = "//div[@id='city']") WebElement ucityclick;
	@FindBy(xpath = "//div[@id='city']//input") WebElement ucityip;
	@FindBy(id = "submit") WebElement submit;

	public void enterfname(String str) {
		scroll(0,200);
		if(str!=null)
			fname.sendKeys(str);
	}

	public void enterlname(String str) {
		if(str!=null)
			lname.sendKeys(str);
	}

	public void enteremail(String str) {
		scroll(0, 200);
		if(str!=null)
			uemail.sendKeys(str);
	}

	public void entergender(String str) {
		if(str!=null) {
			By gender = By.xpath("//label[text()='"+str+"']");
			driver.findElement(gender).click();	
		}
	}

	public void enternumber(String str) {
		scroll(0, 200);
		if(str!=null)
			unumber.sendKeys(str);
	}
	public void enterdob(String str) throws Exception {
		if(str!=null)
			udob.sendKeys(Keys.CONTROL + "a");
		udob.sendKeys(str);
		udob.sendKeys(Keys.ENTER);
	}
	public void entersubs(String [] str1) {
		for (String string1 : str1) {
			usubinput.click();
			usubinput.sendKeys(string1);
			usubinput.sendKeys(Keys.ENTER);
			waits(usubwait);
		}
	}
	public void enterhobby(String [] str2) {
		for (String string2 : str2) {
			//			System.out.println(string2);
			By uhobbies = By.xpath("//label[text()='"+string2+"']");
			driver.findElement(uhobbies).click();
		}
	}
	public void enterphoto(String str) {
		if(str!=null)
			upic.sendKeys(str);
		scroll(0, 200);
	}

	public void enteraddress(String str) {

		if(str!=null)
			uaddress.sendKeys(str);
		scroll(0, 200);
	}
	public void enterstate(String str) {
		if(str!=null)
			ustateclick.click();
		ustateip.sendKeys(str);
		ustateip.sendKeys(Keys.ENTER);
	}
	public void entercity(String str) {
		if(str!=null)
			ucityclick.click();
		ucityip.sendKeys(str);
		ucityip.sendKeys(Keys.ENTER);
	}
	public void entersubmit() {
		submit.click();
	}

}
