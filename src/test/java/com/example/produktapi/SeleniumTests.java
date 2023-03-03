package com.example.produktapi;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

public class SeleniumTests {

    private static WebDriver driver;

    private static WebDriverWait wait;

    @BeforeAll
    static void startDriver() {

        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, Duration.of(10, SECONDS));

        driver.navigate().to("https://java22.netlify.app/");

    }

    @BeforeEach
    void resetToStartPage() {

        WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/a"));

        button.click();

    }

    @AfterAll
    static void quitDriver() {

        driver.quit();

    }

    @Test
    void checkTheTitleOfTheWebApp() {

        driver.navigate().to("https://java22.netlify.app/");

        assertEquals("Webbutik", driver.getTitle());

    }

    @Test
    void checkTextOfH1OnTopOfStartPage() {

        String h1Text = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div/h1")).getText();

        assertEquals("Testdriven utveckling - projekt", h1Text);

    }

    @Test
    void checkTheNumberOfProductsOnTheStartPageIs20() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, SECONDS));

        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("productItem")));

        assertEquals(20, products.size());

    }

    @Test
    void checkAllCategories_haveCorrectName() {

        List<WebElement> categories = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("menuLink")));

        assertEquals("electronics", categories.get(0).getText());
        assertEquals("jewelery", categories.get(1).getText());
        assertEquals("men's clothing", categories.get(2).getText());
        assertEquals("women's clothing", categories.get(3).getText());
    }

    @Test
    void clickOnCategory_electronics_get6ProductsOnPage() {

        WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/a"));

        button.click();

        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("productItem")));

        assertEquals(6, products.size());
    }

    @Test
    void clickOnCategory_jewelery_get4ProductsOnPage() {

        WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/a"));

        button.click();

        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("productItem")));

        assertEquals(4, products.size());
    }

    @Test
    void clickOnCategory_mensClothing_get4ProductsOnPage() {

        WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[4]/a"));

        button.click();

        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("productItem")));

        assertEquals(4, products.size());
    }

    @Test
    void clickOnCategory_womensClothing_get6ProductsOnPage() {

        WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[5]/a"));

        button.click();

        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("productItem")));

        assertEquals(6, products.size());
    }

    @Test
    void checkPictureIsDisplayedForAProduct1() {

        WebElement productImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='card-img-top' and contains(@src, 'https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg')]")));

        assertTrue(productImage.isDisplayed());

    }

    @Test
    void checkPictureIsDisplayedForAProduct2() {

        WebElement productImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='card-img-top' and contains(@src, 'https://fakestoreapi.com/img/51eg55uWmdL._AC_UX679_.jpg')]")));

        assertTrue(productImage.isDisplayed());

    }

    @Test
    void checkPictureIsDisplayedForAProduct3() {

        WebElement productImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@class='card-img-top' and contains(@src, 'https://fakestoreapi.com/img/61mtL65D4cL._AC_SX679_.jpg')]")));

        assertTrue(productImage.isDisplayed());

    }

    @Test
    void checkPictureTagHasTheCorrectSrc1() {
        //*[@id="productsContainer"]/div/div[18]/div/img
        WebElement productImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"productsContainer\"]/div/div[18]/div/img")));

        assertEquals("https://fakestoreapi.com/img/71z3kpMAYsL._AC_UY879_.jpg", productImage.getAttribute("src"));
    }

    @Test
    void checkPictureTagHasTheCorrectSrc2() {
        //*[@id="productsContainer"]/div/div[18]/div/img
        WebElement productImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"productsContainer\"]/div/div[4]/div/img")));

        assertEquals("https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", productImage.getAttribute("src"));
    }

    @Test
    void checkPictureTagHasTheCorrectSrc3() {
        //*[@id="productsContainer"]/div/div[18]/div/img
        WebElement productImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"productsContainer\"]/div/div[16]/div/img")));

        assertEquals("https://fakestoreapi.com/img/81XH0e8fefL._AC_UY879_.jpg", productImage.getAttribute("src"));
    }

    @Test
    void checkPriceOfRandomProductSample1() {

        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("card-text")));

        Pattern pattern = Pattern.compile(".*\n(.*)");
        Matcher matcher = pattern.matcher(prices.get(0).getText());

        String price = matcher.find() ? matcher.group(1) : "";

        assertEquals("109.95", price);

    }

    @Test
    void checkPriceOfRandomProductSample2() {

        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("card-text")));

        Pattern pattern = Pattern.compile(".*\n(.*)");
        Matcher matcher = pattern.matcher(prices.get(8).getText());

        String price = matcher.find() ? matcher.group(1) : "";

        assertEquals("64", price);

    }

    @Test
    void checkPriceOfRandomProductSample3() {

        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("card-text")));

        Pattern pattern = Pattern.compile(".*\n(.*)");
        Matcher matcher = pattern.matcher(prices.get(16).getText());

        String price = matcher.find() ? matcher.group(1) : "";

        assertEquals("39.99", price);

    }

    @Test
    void checkPriceOfRandomProductSample4() {

        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("card-text")));

        Pattern pattern = Pattern.compile(".*\n(.*)");
        Matcher matcher = pattern.matcher(prices.get(5).getText());

        String price = matcher.find() ? matcher.group(1) : "";

        assertEquals("168", price);

    }

    @Test
    void checkPriceOfRandomProductSample5() {

        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("card-text")));

        Pattern pattern = Pattern.compile(".*\n(.*)");
        Matcher matcher = pattern.matcher(prices.get(13).getText());

        String price = matcher.find() ? matcher.group(1) : "";

        assertEquals("999.99", price);

    }

    @Test
    void checkPriceOfRandomProductSample6() {

        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("card-text")));

        Pattern pattern = Pattern.compile(".*\n(.*)");
        Matcher matcher = pattern.matcher(prices.get(19).getText());

        String price = matcher.find() ? matcher.group(1) : "";

        assertEquals("12.99", price);

    }

    @Test
    void checkThatAllProducts_haveTheCorrectName() {

        List<WebElement> productNames = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("card-title")));

        String[] titles = {
                "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                "Mens Casual Premium Slim Fit T-Shirts",
                "Mens Cotton Jacket",
                "Mens Casual Slim Fit",
                "John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet",
                "SolGold Petite Micropave",
                "White Gold Plated Princess",
                "Pierced Owl Rose Gold Plated Stainless Steel Double",
                "WD 2TB Elements Portable External Hard Drive - USB 3.0",
                "SanDisk SSD PLUS 1TB Internal SSD - SATA III 6 Gb/s",
                "Silicon Power 256GB SSD 3D NAND A55 SLC Cache Performance Boost SATA III 2.5",
                "WD 4TB Gaming Drive Works with Playstation 4 Portable External Hard Drive",
                "Acer SB220Q bi 21.5 inches Full HD (1920 x 1080) IPS Ultra-Thin",
                "Samsung 49-Inch CHG90 144Hz Curved Gaming Monitor (LC49HG90DMNXZA) – Super Ultraw Screen QLED",
                "BIYLACLESEN Women's 3-in-1 Snowboard Jacket Winter Coats",
                "Lock and Love Women's Removable Hooded Faux Leather Moto Biker Jacket",
                "Rain Jacket Women Windbreaker Striped Climbing Raincoats",
                "MBJ Women's SolShort Sleeve Boat Neck V",
                "Opna Women's Short Sleeve Moisture",
                "DANVOUY Womens T Shirt Casual Cotton Short"
        };

        String[] titlesFromWebb = new String[20];

        for (int i = 0; i < productNames.size(); i++) {

            titlesFromWebb[i] = productNames.get(i).getText();
        }

        assertEquals(Arrays.toString(titles), Arrays.toString(titlesFromWebb));
    }
}
