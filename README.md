# ITWorx_Assesment_Advanced
ITWorx Technical Automation Assesment 
Assesment is as below 

1- Open the url https://footballstatsdirect.com/home 
2- Make sure English Premier League     is selected 
3- Verify the team with the highest points is Chelsea (value in Points column)
4- Verify the team with the highest goals is Liverpool (value in Goals For column)
5- Click . From the context menu icon   for the Club column, select the filter icon, write “Liverpool” in the search criteria field to filter by “Liverpool”
6- Print the names of the players displayed in the column Name in a text file
7- Implement the above and verify the results while taking into consideration that you use the DRY principal, DDT and POM

Note: Assume that there are more than 2 columns to get the highest cell value for. There could be more columns on the same grid or on other grids on other pages. Try to find a solution to fit for all

2- All the above points has been applied on my project and the code is up and runnning <br />
3- in order to run the project , right click on "FootballStatusTestCases" --> run as TestNG Test
4- Soft Assertions is used in order not to quit if the verification failed <br />
5- if verification status needed , kindly uncomment this line --> FootballStatusTestCases.java --> AfterTest --> endReport() --> uncomment "softassert.assertall"<br />
6- please check all the logging and Reporting (Console , itworx.log , test-output related reposrts , , ITworxReport (Under Report) , screenshoots are provided for this report )<br />
7- DDT approach is used to verify points 3,4 and dry principles are applied as well (using @DataProvider ) Annotation<br />
8- Excel sheet is attached in the same project <br />
9- text file which is used to print output in step 6 also included in the same project (will be created if not exist )<br />
10- POI Jars are included on the same working directory <br />
11- please check 'build path ' configuration to make sure that POI jars are included properly <br />
12- Maven , POM , testNG , POI , POM , Selenium , Java , Log4j , extent reports are used to build my project <br />
13- Bases clases (PageBase , TestBase ) are used for common functions .. which might be reused in multiple places <br />
14- Config file is used as well to put all important configuration like (Page URL , selected language , .... )<br />

