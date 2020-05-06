package first21Days;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ajio {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		// disable the notifications

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		options.addArguments("--incognito");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		// Launching Chrome Browser
		ChromeDriver driver = new ChromeDriver(options);
		// To maximize the browser
		driver.manage().window().maximize();

		// implicitly wait
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) driver;

//			1) Go to https://www.ajio.com/shop/sale
		driver.get("https://www.ajio.com/shop/sale");
		Thread.sleep(3000);
		driver.findElementByXPath("//div[@class='ic-close-quickview']").click();
		Thread.sleep(1000);
//			2) Enter Bags in the Search field and Select Bags in Women Handbags
		driver.findElementByXPath("//input[@class='react-autosuggest__input react-autosuggest__input--open']")
				.sendKeys("Bags");
		Thread.sleep(1000);
		driver.findElementByXPath("(//span[contains(text(),'Bags in')])[1]").click();
		Thread.sleep(2000);

//			3) Click on five grid and Select SORT BY as "What's New"
		driver.findElementByXPath("//div[@class='five-grid']").click();
		Thread.sleep(500);
		WebElement eleSort = driver.findElementByXPath("//div[@class='filter-dropdown']//select");
		Select sltSort = new Select(eleSort);
		sltSort.selectByVisibleText("What's New");

//			4) Enter Price Range Min as 2000 and Max as 5000
		driver.findElementByXPath("//span[text()='price']").click();
		Thread.sleep(500);
		driver.findElementById("minPrice").sendKeys("2000");
		driver.findElementById("maxPrice").sendKeys("5000");
		driver.findElementByXPath("//button[@class='rilrtl-button ic-toparw']").click();
		driver.executeScript("window.scrollBy(0,-1400)");
		Thread.sleep(2000);
//			5) Click on the product "Puma Ferrari LS Shoulder Bag"
		WebElement eleBag = driver
				.findElementByXPath("//div[text()='Puma']/following-sibling::div[text()='Ferrari LS Shoulder Bag']");
		js.executeScript("arguments[0].click()", eleBag);

		Set<String> allWindows = driver.getWindowHandles();
		List<String> allLists = new ArrayList<String>(allWindows);
		driver.switchTo().window(allLists.get(1));
		Thread.sleep(3000);

//			6) Verify the Coupon code for the price above 2690 is applicable for your product, 
//			if applicable the get the Coupon Code and Calculate the discount price for the coupon
		String strOriPrice = driver.findElementByXPath("//div[@class='prod-sp']").getText();
		String repOriPrice = strOriPrice.replaceAll("[^0-9]", "");
		int intOriPrice = Integer.parseInt(repOriPrice);
		System.out.println("The Price of the Product is :"+intOriPrice);
		String strUseCode = "";
		if (intOriPrice > 2690) {
			strUseCode = driver.findElementByXPath("//div[@class='promo-title']").getText();
		}
		// System.out.println(strUseCode);
		String strUseCodeValue = strUseCode.substring(9);
		System.out.println("The Use Code is: " + strUseCodeValue);
		String strDisPrice = driver.findElementByXPath("//div[@class='promo-discounted-price']").getText();
		String repDisPrice = strDisPrice.replaceAll("[^0-9]", "");
		int intDisPrice = Integer.parseInt(repDisPrice);
		int intCalulatedDis = intOriPrice - intDisPrice;
		System.out.println("The difference offer price is : " + intCalulatedDis);

//			7) Check the availability of the product for pincode 560043, 
		// print the expected delivery date if it is available
		try {
			driver.findElementByXPath("//span[text()='Enter pin-code to know estimated delivery date.']").click();
			driver.findElementByName("pincode").sendKeys("560043");
			driver.findElementByXPath("//button[@class='edd-pincode-modal-submit-btn']").click();
			Thread.sleep(1000);

			String strDeliveryDate = driver
					.findElementByXPath("//span[@class='edd-message-success-details-highlighted']").getText();
			System.out.println("The Expected Delivery date is : " + strDeliveryDate);
		} catch (Exception e) {
			System.out.println("The Expected Delivery is not available");
		}
//			8) Click on Other Informations under Product Details and Print the Customer Care address, phone and email
		driver.findElementByXPath("//div[@class='other-info-toggle']").click();
		Thread.sleep(1000);
		String strCustAddress = driver.findElementByXPath(
				"//li[@class='detail-list mandatory-info']//span[text()='Customer Care Address']/following-sibling::span[@class='other-info']")
				.getText();
		System.out.println("The Customer care address is : " + strCustAddress);

//			9) Click on ADD TO BAG and then GO TO BAG
		WebElement eleAddToCart = driver.findElementByXPath("//span[text()='ADD TO BAG']");
		js.executeScript("arguments[0].click()", eleAddToCart);
		Thread.sleep(3000);
		WebElement eleGoToBag = driver.findElementByXPath("//span[text()='GO TO BAG']");
		js.executeScript("arguments[0].click()", eleGoToBag);

//			10) Check the Order Total before apply coupon
		String strOrdTotBeforeCoup = driver.findElementByXPath("//span[@class='price-value bold-font']").getText();
		System.out.println("The Order total before applying the coupon : " + strOrdTotBeforeCoup);
		String repOrdTot = strOrdTotBeforeCoup.replaceAll("[^0-9]", "");
		int intOrdTot = Integer.parseInt(repOrdTot);

//			11) Enter Coupon Code and Click Appl
		driver.findElementById("couponCodeInput").sendKeys(strUseCodeValue);
		driver.findElementByXPath("//button[text()='Apply']").click();
		Thread.sleep(3000);
//			12) Verify the Coupon Savings amount(round off if it in decimal) 
		// under Order Summary and the matches the amount calculated in Product details
		String strCouponSavings = driver
				.findElementByXPath(
						"//span[text()='Coupon savings']//following::span[@class='price-value discount-price']")
				.getText();
		System.out.println("The Coupon Savings amount is : " + strCouponSavings);
		String coupSavingPrice = strCouponSavings.substring(4);
		double dblCoupSavingPrice = Double.parseDouble(coupSavingPrice);
		int intCoupSavingPrice = (int) Math.round(dblCoupSavingPrice);
		System.out.println("The Rounded amount is : " +intCoupSavingPrice);
		if (intCoupSavingPrice == intCalulatedDis) {
			System.out.println("The discounted amount matches with the amount caluculated in product details");
		} else {
			System.out.println("The discounted amount not matches with the amount caluculated in product details");

		}
//			13) Click on Delete and Delete the item from Bag
		driver.findElementByXPath("//div[text()='Delete']").click();
		Thread.sleep(500);
		driver.findElementByXPath("//div[text()='DELETE']").click();
		Thread.sleep(2000);
//			14) Close all the browsers
		driver.quit();
	}

}
