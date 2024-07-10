package com.kiwiqa.demoqatestng;

import java.time.Duration;

import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.kiwiqa.testngdemoqa.DemoQAFillFormPage;
import com.kiwiqa.testngdemoqa.DemoQAVerifyFormPage;
import com.kiwiqa.utils.UtilFile;
public class FullFlowDemoQA extends UtilFile implements ITestListener{
	DemoQAFillFormPage fillForm;
	DemoQAVerifyFormPage verifyform;
	public ExtentTest testFormFill;
	public ExtentTest testOpenWebsite; 
	public ExtentTest TestVerifyData;

	@Parameters("Browser")
	@BeforeSuite(description = "before suite give drivers" ,groups = "mandatory")
	public void initAll(String Browser) throws Exception {
		reportgen();
		innitall();
		giveDrivers(Browser);
		xlsInput("Demoinputs.xls");
		fillForm = new DemoQAFillFormPage(driver);
		verifyform = new DemoQAVerifyFormPage(driver);
		testFormFill = extent.createTest("Form Fill", "Form Filling");
		TestVerifyData = extent.createTest("Form verify", "Form verify");
	}
	@BeforeMethod(description = "before method",groups = "mandatory")
	public void openWebsite() {
		testOpenWebsite = extent.createTest("WebSite Open Test", "Website opening test");
		driver.get("https://demoqa.com/automation-practice-form");
		takeScreenShot("website opened");
		testOpenWebsite.pass("Website opened",MediaEntityBuilder.createScreenCaptureFromPath("./screenshot/DemoQA/website opened.png").build());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}
	@Test(testName = "Fill Form",description = "fill form",groups = "form fill") 
	public void formFill() throws Exception {
		fillForm.enterfname(xlsvalues.get("FirstName"));
		fillForm.enterlname(xlsvalues.get("LastName"));
		fillForm.enteremail(xlsvalues.get("Email"));
		fillForm.entergender(xlsvalues.get("Gender"));
		fillForm.enternumber(xlsvalues.get("Mobile"));
		fillForm.enterdob(xlsvalues.get("DOB"));
		fillForm.entersubs(subsarr);
		fillForm.enterhobby(hobbarr);
		//		Assert.assertTrue(false);
		fillForm.enterphoto(System.getProperty("user.dir")+"\\"+xlsvalues.get("Picure"));
		fillForm.enteraddress(xlsvalues.get("Adress"));
		fillForm.enterstate(xlsvalues.get("State"));
		fillForm.entercity(xlsvalues.get("City"));
		fillForm.entersubmit();
		takeScreenShot("form filing pass");
		testFormFill.pass("form filing pass", MediaEntityBuilder.createScreenCaptureFromPath("./screenshot/DemoQA/form filing pass.png").build());


	}
	@AfterMethod(description = "Verify method",groups = "verify form")
	public void check(ITestResult result) throws Exception
	{
		System.out.println("after method");
		if(result.getStatus()==1) 
		{
			verifyform.waitfortext();
			int testcase = verifyform.verifyip("./input.txt");
			if(testcase==0) 
			{
				takeScreenShot("pass at "+result.getName());
				TestVerifyData.pass("pass at ",MediaEntityBuilder.createScreenCaptureFromPath("./screenshot/DemoQA/pass at "+result.getName()+".png").build());
			}
			else if (testcase>0)
			{
				takeScreenShot("fail at "+result.getName());
				TestVerifyData.fail("fail at "+result.getName(), MediaEntityBuilder.createScreenCaptureFromPath("./screenshot/DemoQA/fail at "+result.getName()+".png").build());
			}
		}
		else if(result.getStatus()==2)
		{
			System.out.println("in result fail");
			takeScreenShot("fail at "+result.getName());
			testFormFill.fail("fail at "+result.getName(), MediaEntityBuilder.createScreenCaptureFromPath("./screenshot/DemoQA/fail at "+result.getName()+".png").build());
		}
	}
	@AfterTest(description = "driver quit",groups = "mandatory")
	public void driverQuit() {
		extent.flush();
		driver.quit();

	}




}
