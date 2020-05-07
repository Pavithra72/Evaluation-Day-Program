package first21Days;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class Azure {

	public static void main(String[] args) throws InterruptedException {
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("download.default_directory", "C:\\Users\\Lenovo\\Downloads");
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		// disable the notifications

		options.addArguments("--disable-notifications");
		// Launching Chrome Browser
		ChromeDriver driver = new ChromeDriver(options);

		// To maximize the browser
		driver.manage().window().maximize();

		// implicitly wait
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		// WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions actions = new Actions(driver);

//			1) Go to https://azure.microsoft.com/en-in/
		driver.get("https://azure.microsoft.com/en-in/");
		Thread.sleep(3000);

//			2) Click on Pricing
		driver.findElementById("navigation-pricing").click();
		Thread.sleep(2000);

//			3) Click on Pricing Calculator
		driver.findElementByXPath("//a[text()[normalize-space()='Pricing calculator']]").click();
		Thread.sleep(2000);

//			4) Click on Containers
		driver.findElementByXPath("//button[@value='containers']").click();
		Thread.sleep(2000);

//			5) Select Container Instances
		driver.findElementByXPath("(//span[text()='Container Instances'])[3]").click();
		Thread.sleep(500);

//			6) Click on Container Instance Added View
		driver.findElementByXPath("//a[@id='new-module-loc']").click();
		Thread.sleep(1000);

//			7) Select Region as "South India"
		WebElement eleRegion = driver.findElementByXPath("(//label[@for='region']/following-sibling::select)[1]");
		Select sltRegion = new Select(eleRegion);
		sltRegion.selectByVisibleText("South India");

//			8) Set the Duration as 180000 seconds
		WebElement eleDuration = driver.findElementByXPath("(//input[@name='seconds'])[1]");
		actions.doubleClick(eleDuration).perform();
		eleDuration.sendKeys("180000");

//			9) Select the Memory as 4GB
		WebElement eleMemory = driver.findElementByXPath("(//select[@name='memory'])[1]");
		Select sltMemory = new Select(eleMemory);
		sltMemory.selectByVisibleText("4 GB");

//			10) Enable SHOW DEV/TEST PRICING
		driver.findElementByXPath("//button[@id='devtest-toggler']").click();
		Thread.sleep(1000);

//			11) Select Indian Rupee  as currency
		WebElement eleCurrency = driver.findElementByXPath("//select[@class='select currency-dropdown']");
		Select sltCurrency = new Select(eleCurrency);
		sltCurrency.selectByVisibleText("Indian Rupee (₹)");
		Thread.sleep(1000);

//			12) Print the Estimated monthly price
		String strEstimatedPrice = driver
				.findElementByXPath("(//div[@class='column large-3 text-right total']//span)[4]").getText();
		System.out.println("The Estimated Monthly Price is : " + strEstimatedPrice);

//			13) Click on Export to download the estimate as excel
		driver.findElementByXPath("(//button[contains(@class,'calculator-button button-transparent')])[4]").click();
		Thread.sleep(2000);

//			14) Verify the downloded file in the local folder
		File file = new File("C:\\Users\\Lenovo\\Downloads\\ExportedEstimate.xlsx");
		if (file.exists()) {
			System.out.println("file exists for Container Instance  in the specified path");
		}

//			15) Navigate to Example Scenarios and Select CI/CD for Containers
		driver.executeScript("window.scrollBy(0,-3000)");

		driver.findElementByXPath("//a[text()='Example Scenarios']").click();
		Thread.sleep(1000);

//			16) Click Add to Estimate
		WebElement eleAddToEst = driver.findElementByXPath("//button[text()='Add to estimate']");
		js.executeScript("arguments[0].click()", eleAddToEst);

		Thread.sleep(1000);

//			17) Change the Currency as Indian Rupee
		WebElement eleCurrency1 = driver.findElementByXPath("//select[@class='select currency-dropdown']");
		Select sltCurrency1 = new Select(eleCurrency1);
		sltCurrency1.selectByVisibleText("Indian Rupee (₹)");
		Thread.sleep(1000);

//			18) Enable SHOW DEV/TEST PRICING
		WebElement eleToggle = driver.findElementByXPath("//button[@id='devtest-toggler']");
		js.executeScript("arguments[0].click()", eleToggle);

		Thread.sleep(1000);

//			19) Export the Estimate
		WebElement eleExport = driver
				.findElementByXPath("(//button[contains(@class,'calculator-button button-transparent')])[4]");
		js.executeScript("arguments[0].click()", eleExport);

		Thread.sleep(2000);

//			20) Verify the downloded file in the local folder
		File file1 = new File("C:\\Users\\Lenovo\\Downloads\\ExportedEstimate (1).xlsx");
		if (file1.exists()) {
			System.out.println("file exists for Container Instance after adding estimate in the specified path");
		}

		driver.close();
	}

}
