package itWorx.Bases;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;




public class TestBase {

	public static Properties properties;
	public static  WebDriver driver;
	public static String browserType;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static  String log4jConfPath;
	final   static Logger logger = Logger.getLogger(TestBase.class);
	static {


		System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
		System.setProperty("webdriver.ie.driver", "./Drivers/IEDriverServer.exe");
		System.setProperty("webdriver.gecko.driver","./Drivers/geckodriver.exe");

		properties = ReadProperties("./config/config.properties");
		browserType = properties.getProperty("browser");
		log4jConfPath=  properties.getProperty("log4jPropertiesPath");
		PropertyConfigurator.configure(log4jConfPath);
		System.out.println("properties Initialized ");
		extent = new ExtentReports(properties.getProperty("testReportPath"),true);
		extent.loadConfig(new File(properties.getProperty("extentReportConfig")));

	}
	
	
	
	public  static Properties ReadProperties(String FilePath) {

		try {
			FileInputStream testProperties = new FileInputStream(FilePath);
			properties = new Properties();
			properties.load(testProperties);
			return properties;
		} catch (FileNotFoundException e) {
			logger.error("Test base Constractor " + e.getMessage());
		} catch (IOException e) {
			logger.error("Test base Constractor " + e.getMessage());
		}
		return properties;
	}
	
	public static void  openPage(String pageUrl)
	{

		driver = new ChromeDriver();
		// Open the URL
		logger.info("Opening Browser ...");
		logger.info("Open URL = " + pageUrl);
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.navigate().to(pageUrl);
		
	}
	
	public static void closeBrowser()
	{
		try {

			logger.info("Closing Browser ...");
			driver.quit();
		} catch (Exception e) {
			logger.error("Close Browser" + e.getMessage());
		}
	}
	
	 public String[][] getExcelData(String fileName, String sheetName) throws IOException {
	        String[][] data = null;
	        try {
	             
	            FileInputStream fis = new FileInputStream(fileName);
	            XSSFWorkbook workbook = new XSSFWorkbook(fis);
	            XSSFSheet sheet = workbook.getSheet(sheetName);
	            XSSFRow row = sheet.getRow(0);
	            int noOfRows = sheet.getPhysicalNumberOfRows();
	            int noOfCols = row.getLastCellNum();
	            XSSFCell cell;
	            data = new String[noOfRows - 1][noOfCols];
	 
	            for (int i = 1; i < noOfRows; i++) {
	                for (int j = 0; j < noOfCols; j++) {
	                    row = sheet.getRow(i);
	                    cell = row.getCell(j);
	                    data[i - 1][j] = cell.getStringCellValue();
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("The exception is: " + e.getMessage());
	        }
	        return data;
	    }
	 
	 public static String takeScreenShot(String screenshotName)  {
		   String path =System.getProperty("user.dir") + properties.getProperty("screenShotsPath") ;
      //below line is just to append the date format with the screenshot name to avoid duplicate names 
      String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				TakesScreenshot ts = (TakesScreenshot) driver;
				File source = ts.getScreenshotAs(OutputType.FILE);
				          //after execution, you could see a folder "FailedTestsScreenshots" under src folder
				String destination =path+screenshotName+dateName+".png";
				File finalDestination = new File(destination);
				try {
					FileUtils.copyFile(source, finalDestination);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				          //Returns the captured file path
				return destination;
}
}
