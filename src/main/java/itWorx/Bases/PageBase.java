package itWorx.Bases;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PageBase {
	Map<String,Integer> map = new HashMap();
	public void writeInTextFile(List<WebElement> playersNames , String fileName) throws IOException, InterruptedException
	{
	 
	 FileWriter writer = new FileWriter(fileName); 
     for(WebElement player : playersNames)
     {
    	 System.out.println("-------"+player.getText()+"-----------");
    	 writer.write(player.getText() + System.lineSeparator());
     }
     writer.close();
	}
	
	public LinkedHashMap<String, Integer> sortColumns (WebDriver driver, String firstColumn , String secondColumn)
	{
		List<WebElement> teamNames = retrieveAllColumnValues(driver , firstColumn);
		List<WebElement> pointsORGoals = retrieveAllColumnValues(driver, secondColumn);
		ArrayList<String> teamNamesStr = convertToString(teamNames);
		ArrayList<Integer> intPointsOrGoals = convertToInteger(pointsORGoals);
		System.out.println("--------------------------------------------");
		System.out.println ("Below is the list of "+firstColumn+" along with "+secondColumn+ ":-");
		System.out.println("--------------------------------------------");
		map = createMap(teamNamesStr , intPointsOrGoals);
		System.out.println("--------------------------------------------");
		LinkedHashMap<String, Integer> sortedMap = sortMapAsc(map , intPointsOrGoals);
		System.out.println ("Below is the list of "+firstColumn+" along with "+secondColumn+ " after sorting :-");
		System.out.println("--------------------------------------------");
		System.out.println(sortedMap);

		return sortedMap;
	}
	
	Map<String,Integer> createMap (ArrayList<String> key , ArrayList<Integer> value)
	{

		for (int i = 0 ; i < key.size();i++)
		{
			map.put(key.get(i),value.get(i));
			System.out.println(key.get(i) + " : " +value.get(i));	
		}
		return map;
	}

	public LinkedHashMap<String, Integer> sortMapAsc(Map<String,Integer> map , ArrayList<Integer> intPoints)
	{
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap();
		Collections.sort(intPoints);
		for (int num : intPoints) {
			for (Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue().equals(num)) {
					sortedMap.put(entry.getKey(), num);
				}
			}   
		}
		return sortedMap;
	}

	public ArrayList<Integer> getAllPointsInIntegers(WebDriver driver,String columnName)
	{
		List<WebElement> pointsORGoals = retrieveAllColumnValues(driver , columnName);
		ArrayList<Integer> intPointsOrGoals = convertToInteger(pointsORGoals);
		return intPointsOrGoals;
	}

	public List<WebElement> retrieveAllColumnValues(WebDriver driver,String columnName)
	{
		return driver.findElements(By.xpath("//div[@col-id='"+columnName+"']"));
	}

	public ArrayList<Integer> convertToInteger(List<WebElement> points)
	{
		ArrayList<Integer> intPoints = new ArrayList<Integer>();
		for (int i = 1 ; i < points.size();i++)
		{
			int point = Integer.parseInt(points.get(i).getText());
			intPoints.add(point);
		}
		return intPoints;
	}

	public ArrayList<String> convertToString(List<WebElement> teamNames)
	{
		ArrayList<String> teamNamesStr = new ArrayList<String>();
		for (int i = 1 ; i < teamNames.size();i++)
		{
			String teamNameStr = teamNames.get(i).getText();
			teamNamesStr.add(teamNameStr);
		}
		return teamNamesStr;
	}

	



}
