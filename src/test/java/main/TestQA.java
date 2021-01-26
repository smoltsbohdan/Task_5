package main;

import constants.Constants;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Test;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TestQA {

    private WebDriver driver;
    private List<WebElement> resultLinks;
    private WebDriverWait wait;

    @Before
    public void before(){
        System.setProperty(Constants.INSTANCE.getPROPERTY_KEY(), Constants.INSTANCE.getPROPERTY_VALUE());
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(Constants.INSTANCE.getGOOGLE_LINK());

        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("automation");
        element.submit();

        resultLinks = driver.findElements(By.className("g"));
    }

    @After
    public void after(){
        driver.quit();
    }

    @Test
    public void checkResultsTheFirstPage() {
        if(resultLinks.size() == Constants.INSTANCE.getSEARCH_RESULT_ON_THE_FIRST_PAGE()){
            checkTheTotalSearchResults();
        }else System.out.println("Search result on the first page is less than 10");
    }

    @Test
    public void checkTheTotalSearchResults(){
        WebElement result_stats = driver.findElement(By.id("result-stats"));
       int totalResult = Integer.parseInt(
               result_stats.getText()
                       .replaceAll("\\([^)]+\\)", "")
                       .replaceAll("[^0-9]", "")
       );

        if(totalResult > Constants.INSTANCE.getTOTAL_SEARCH_RESULT()){
            check_First_Page();
        }else System.out.println("Total search result is less than 100 000 000");

    }

    @Test
    public void check_First_Page(){
        if(!Constants.INSTANCE.getWIKI_LINK().equals(
                resultLinks.get(0).
                        findElement(By.tagName("a")).
                        getAttribute("href"))){
            System.out.println("On the first page the link is not https://en.wikipedia.org/wiki/Automation");
        }
    }
}
