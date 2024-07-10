package com.kiwiqa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.HashMap;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
public class UtilFile {
	protected WebDriver driver;
	protected ExtentReports extent;
//	protected ExtentTest test;

	protected HashMap<String,String> xlsvalues,xlsxvalues;
	protected String[] subsarr,hobbarr;
	String subs,hobb;
	DataFormatter dataFormatter ;
//	protected Properties prop ;


	public void innitall() {
		
		dataFormatter = new DataFormatter();
	}
	public void giveDrivers(String browser) throws Exception {
//		prop = new Properties();
//		FileInputStream fis = new FileInputStream("config.properties");
//
//		prop.load(fis);
		if(browser.equalsIgnoreCase("Chrome")) {
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("FireFox")) {
			driver = new FirefoxDriver();
		}
	}	

	public void scroll(WebElement ele) {
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", ele);
	}
	public void scroll(int i,int j) {
		((JavascriptExecutor)driver).executeScript("window.scrollBy("+i+","+j+")", "");
	}
	public void waits(WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
	public void softAssert(String actual ,String expect , String failmessage) {
		SoftAssert softassert = new SoftAssert();
		softassert.assertEquals(actual, expect,failmessage);
	}
	public void hardAssert(String actual ,String expect , String failmessage) {
		Assert.assertEquals(actual, expect,failmessage);
	}
	public void takeScreenShot(String str)  {

		try {
			System.out.println("in try"); 
			TakesScreenshot scrShot = (TakesScreenshot)driver;
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile=new File("./Screenshot/DemoQA/"+str+".png");
			FileHandler.copy(SrcFile, DestFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
			System.out.println(e.getMessage());
		} 
	}

	public void reportgen() {
		System.out.println("report generate");
		//		ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extentReport.html");
		//		  htmlReporter.config().setTheme(Theme.STANDARD);
		//	      htmlReporter.config().setDocumentTitle("Automation Report");
		//	      htmlReporter.config().setReportName("OrangeHRM Myinfo Report");
		//		//create ExtentReports and attach reporter(s)
		//		  ExtentReports extent = new ExtentReports();
		//		  extent.attachReporter(htmlReporter);
		//     // Create a test instance


		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("extentReport.html");
		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("Automation Report");
		spark.config().setReportName("DemoQa Form Fill Report");
		extent.attachReporter(spark);
//		ExtentTest test = extent.createTest("WebSite Open Test", "Website opening test");

		System.out.println("finish");
	}
	public void xlsxInput(String str) throws Exception
	{
		xlsxvalues = new HashMap<String, String>();
		FileInputStream fis=new FileInputStream(new File(str));

		XSSFWorkbook wb = new XSSFWorkbook(fis);   
		XSSFSheet sheet = wb.getSheetAt(0);
		int sizeofrow = sheet.getLastRowNum();
		for(int i=0;i<= sizeofrow;i++) {
			xlsxvalues.put(dataFormatter.formatCellValue(sheet.getRow(i).getCell(0)).toString(), dataFormatter.formatCellValue(sheet.getRow(i).getCell(1)).toString());
		}

	}

	public void xlsInput(String str) throws Exception 
	{
		xlsvalues = new HashMap<String, String>();
		FileInputStream fis=new FileInputStream(new File(str));
		HSSFWorkbook  wb = new HSSFWorkbook(fis);   
		HSSFSheet  sheet = wb.getSheetAt(0);

		int sizeofrow = sheet.getLastRowNum();
		String hobby=null,keyhobby = null ,subs=null,keysubs=null;
		for(int i=0;i<= sizeofrow;i++)
		{
			if(!("Subjects").equals(dataFormatter.formatCellValue(sheet.getRow(i).getCell(0)).toString())&&!("Hobbies").equals(dataFormatter.formatCellValue(sheet.getRow(i).getCell(0)).toString()))
			{
				xlsvalues.put(dataFormatter.formatCellValue(sheet.getRow(i).getCell(0)), dataFormatter.formatCellValue(sheet.getRow(i).getCell(1)));
			}
			else {
				if(("Hobbies").equals(dataFormatter.formatCellValue(sheet.getRow(i).getCell(0)).toString()))
				{
					for(int j=0;j<sheet.getRow(i).getLastCellNum();j++) {
						if(j==0 &&!(dataFormatter.formatCellValue(sheet.getRow(i).getCell(j))).isBlank()) {
							keyhobby=(dataFormatter.formatCellValue(sheet.getRow(i).getCell(j)));
						}
						else if(!(dataFormatter.formatCellValue(sheet.getRow(i).getCell(j))).isBlank())
						{
							if(j==1)
								hobby=dataFormatter.formatCellValue(sheet.getRow(i).getCell(j));
							else
								hobby=hobby+", "+dataFormatter.formatCellValue(sheet.getRow(i).getCell(j));
						}

					}
				}
				if(("Subjects").equals(dataFormatter.formatCellValue(sheet.getRow(i).getCell(0)).toString()))
				{
					for(int j=0;j<sheet.getRow(i).getLastCellNum();j++) {
						if(j==0 && !(dataFormatter.formatCellValue(sheet.getRow(i).getCell(j))).isBlank()) {

							keysubs=(dataFormatter.formatCellValue(sheet.getRow(i).getCell(j)));
						}
						else if (!(dataFormatter.formatCellValue(sheet.getRow(i).getCell(j))).isBlank())
						{
							if(j==1 && dataFormatter.formatCellValue(sheet.getRow(i).getCell(j))!=" ")
								subs=dataFormatter.formatCellValue(sheet.getRow(i).getCell(j));
							else if (dataFormatter.formatCellValue(sheet.getRow(i).getCell(j))!= " ")
								subs=subs+", "+dataFormatter.formatCellValue(sheet.getRow(i).getCell(j));
						}

					}
				}
			}
		}
		xlsvalues.put(keyhobby,hobby);
		xlsvalues.put(keysubs, subs);
		subsarr= subs.split(", ");
		hobbarr =hobby.split(", "); 

	}
}
