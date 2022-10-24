package itworx.itworx;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.relevantcodes.extentreports.LogStatus;

import itWorx.Bases.TestBase;
import itWorx.Pages.FootballStatusPage;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.WebElement;
import java.io.IOException;

public class FootballStatusTestCases extends TestBase{

	Logger logger = Logger.getLogger(FootballStatusTestCases.class);
	FootballStatusPage footballStatusPage ;
	String actualSelectedLanguage;
	SoftAssert softAssert = new SoftAssert();
	JavascriptExecutor jse=(JavascriptExecutor)driver;
	LinkedHashMap<String, Integer> sortedMapTeamPoints;
	ArrayList<Integer> allPoints_Goals;
	LinkedHashMap<String, Integer> sortedMapTeamGoals;
	ArrayList<Integer> allGoals;
	WebElement top20Scores;
	List<WebElement> playersNames;
	
    @BeforeTest
	public void beforeTest(XmlTest xmlTest) {
		logger.info("Open football page ");
		test = extent.startTest((this.getClass().getSimpleName()+" :: " + xmlTest.getName()), xmlTest.getName());
		test.assignAuthor("Nancy Emad");
		test.assignCategory("itworx-Assesment");
		test.log(LogStatus.PASS,  properties.getProperty("browser")+" Browser Launched Successfully with the desired data" );	
	}
	
	@BeforeMethod
	public void beforeMethod(Method method)
	{
		// open the page each time before method 
		openPage(properties.getProperty("footballURL"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		jse=(JavascriptExecutor)driver;
		footballStatusPage = new FootballStatusPage(driver);
		driver.manage().window().maximize();
	    test = extent.startTest((this.getClass().getSimpleName()+" :: " + method.getName()), method.getName());
		test.assignAuthor("Nancy Emad");
		test.assignCategory("ITWorx-Assesment");
		
	}

	
	@Test
	public void languageVerification() 
	{
		// logging the functionalty of the test in a different ways 
		
		logger.info("Football verification started" );
		System.out.println("Football verification started" );
		System.out.println("--------------------------------------------");
		
		
		//2- verify that English Premier League is selected
		actualSelectedLanguage = footballStatusPage.getSelectedLanguage( );
		System.out.println("Selected Language :"+actualSelectedLanguage);
		logger.info("Selected Language :"+actualSelectedLanguage);
		System.out.println("--------------------------------------------");
		//soft assert in order to verify the language but not to quit if failed 
		softAssert.assertEquals(actualSelectedLanguage, properties.getProperty("EnglishLanguage"));
	}
	
	
		//decrease window size to 50%
	   @Test(dataProvider="SortColumns")
		public void verifyHighestTeam(String column1 , String column2 , String playerName)
		{
		//3- Verify the team with the highest points is Chelsea (value in Points column)
		//4- Verify the team with the highest goals is Liverpool (value in Goals For column)
		// first function is created in order to sort the columns team and points/goals ascending and save it on hashmap 
		// second we get the max points/goals
		// get the value (points/goals) which has the key (team) --> Chelsea/liverpool
		// verify whether max points = Chelsea's points or not 
		 Thread.sleep(1000);
		 jse.executeScript("document.body.style.zoom = '50%';");
		//Thread.sleep(500);
		Thread.sleep(1000);
		sortedMapTeamPoints = footballStatusPage.sortColumns(driver , column1,column2);
		allPoints_Goals=footballStatusPage.getAllPointsInIntegers(driver,column2);
		Collections.sort(allPoints_Goals);
		System.out.println("--------------------------------------------");
		logger.info((playerName)+" has "+sortedMapTeamPoints.get (playerName) + " Points , while max points ="+ allPoints_Goals.get(allPoints_Goals.size()-1));
		System.out.println ((playerName)+" has "+sortedMapTeamPoints.get(playerName)+ " Points , while max points ="+ allPoints_Goals.get(allPoints_Goals.size()-1));
		System.out.println("--------------------------------------------");
		softAssert.assertEquals(sortedMapTeamPoints.get(playerName),allPoints_Goals.get(allPoints_Goals.size()-1));
	
		}
	
	
	@DataProvider(name="SortColumns")
		public Object[][] getDataFromDataprovider() throws IOException{
		Object[][] arrayObject = getExcelData("dataSheet.xlsx","Sheet1");
		return arrayObject;
	}
	
	@Test
	public void getTop20Scores() throws InterruptedException, IOException 
	{
		logger.info("Top 20 scorers started" );
		System.out.println("Top 20 scorers started" );
		System.out.println("--------------------------------------------");
		
		logger.info("Select top 20 scorers tab" );
		footballStatusPage.selectTop20Scorers();
		System.out.println("top 20 scorers selected");
		
		logger.info("filter using Liverpool" );
		footballStatusPage.filterTheClub();
		System.out.println("Liverpool is filtered successfully ");
	
		
		jse.executeScript("document.body.style.zoom = '50%';");
		
	
		logger.info("select players names " );
		playersNames=footballStatusPage.selectPlayersNames();
		System.out.println("Player Names have been selected successfully ");
		
		logger.info("write players in a text file " );
		footballStatusPage.writeInTextFile(playersNames , "output.txt");
		System.out.println("Player Names have been written in text file successfully ");
		 	
		
	}
	
	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		
		switch (result.getStatus()) {
		case ITestResult.FAILURE:
			test.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
			test.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
			test.log(LogStatus.FAIL, "Screenshot below: " + test.addScreenCapture(takeScreenShot(result.getMethod().getMethodName())));
			break;
		case ITestResult.SKIP:
			test.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
			break;
		case ITestResult.SUCCESS:
			test.log(LogStatus.PASS, result.getName()+ " Test Case  is passed");
			test.log(LogStatus.PASS, "Screenshot below: " + test.addScreenCapture(takeScreenShot(result.getMethod().getMethodName())));
			break;
		default:
			break;
		}
		closeBrowser();
	}

	@AfterTest
	public void endReport(){
		extent.endTest(test);
		extent.flush();
		//softAssert.assertAll();
	}

}



  
