package itWorx.Pages;


import java.util.List;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import itWorx.Bases.PageBase;

public class FootballStatusPage extends PageBase{
	WebDriver driver;
	WebElement selectedLanguage;
	WebElement top20Scores;
	WebElement filter;
	WebElement menuFilter;
	WebElement filterInput;
	
	public FootballStatusPage(WebDriver driver)
	{	
		setDriver(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public String getSelectedLanguage()
	{
		selectedLanguage = driver.findElements(By.xpath("//button[@class='MuiButtonBase-root MuiButton-root MuiButton-outlined MuiButtonGroup-grouped MuiButtonGroup-groupedHorizontal MuiButtonGroup-groupedOutlined MuiButtonGroup-groupedOutlinedHorizontal MuiButtonGroup-groupedOutlinedPrimary league-button button-selected MuiButton-outlinedPrimary MuiButton-outlinedSizeLarge MuiButton-sizeLarge']//span")).get(0);
		String actSelectedLanguage = selectedLanguage.getText();
		return actSelectedLanguage;
	}

	
	public void selectTop20Scorers()
	{
		top20Scores = driver.findElement(By.xpath("//span[contains(text(),'Top 20 Scorers')]/parent::button"));
		top20Scores.click();
	}

	public void filterTheClub() throws InterruptedException
	{
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		filter = driver.findElement(By.xpath("//span[contains(text(),'Club')]/parent::div/parent::div/span"));
		filter.click();
		menuFilter = driver.findElement(By.xpath("//span[@role='menuitem']//span[@class='ag-icon ag-icon-filter']"));
		menuFilter.click();
		filterInput = driver.findElement(By.xpath("//div[@class='ag-filter-body']//input"));
		filterInput.sendKeys("Liverpool");
		filterInput.sendKeys(Keys.ENTER);

	}
	
	public List<WebElement> selectPlayersNames() throws InterruptedException
	{
	    Thread.sleep(1000);
		List<WebElement> playersNames=driver.findElements(By.xpath("//div[@col-id='player']//div[@class='ag-react-container']//span"));
		return playersNames;
	}
	


}


