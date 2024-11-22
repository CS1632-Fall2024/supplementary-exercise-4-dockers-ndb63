package edu.pitt.cs;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.FixMethodOrder;
import java.time.Duration;
import org.junit.runners.MethodSorters;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class D3Test {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");
    driver = new ChromeDriver(options);
  }
  //3,4,6,8
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void tEST1LINKS() {
    driver.get("http://localhost:8080//");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.manage().window().setSize(new Dimension(1920, 1016));
    driver.findElement(By.linkText("Reset")).click();
    vars.put("pageurl", js.executeScript("return window.location.href"));
    assertEquals(vars.get("pageurl").toString(), "http://localhost:8080/reset");
  }
  @Test
  public void tEST2RESET() {
    driver.get("http://localhost:8080//rent-a-cat");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.findElement(By.id("rentID")).click();
    driver.findElement(By.id("rentID")).sendKeys("1");
    driver.findElement(By.cssSelector(".form-group:nth-child(3) .btn")).click();
    driver.findElement(By.id("rentID")).click();
    driver.findElement(By.id("rentID")).sendKeys("2");
    driver.findElement(By.cssSelector(".form-group:nth-child(3) .btn")).click();
    driver.findElement(By.id("rentID")).click();
    driver.findElement(By.id("rentID")).sendKeys("3");
    driver.findElement(By.cssSelector(".form-group:nth-child(3) .btn")).click();
    driver.findElement(By.linkText("Reset")).click();
    vars.put("cat1", driver.findElement(By.id("cat-id1")).getText());
    vars.put("cat2", driver.findElement(By.id("cat-id2")).getText());
    vars.put("cat3", driver.findElement(By.id("cat-id3")).getText());
    assertEquals(vars.get("cat1").toString(), "ID 1. Jennyanydots");
    assertEquals(vars.get("cat2").toString(), "ID 2. Old Deuteronomy");
    assertEquals(vars.get("cat3").toString(), "ID 3. Mistoffelees");
  }
  @Test
public void tEST3CATALOG() {
    driver.get("http://localhost:8080/");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.findElement(By.linkText("Catalog")).click();
    {
        WebElement element = driver.findElement(By.xpath("//li[2]/img"));
        String attribute = element.getAttribute("src");
        vars.put("cat2", attribute);
    }

    assertEquals("http://localhost:8080/images/cat2.jpg", vars.get("cat2").toString());
}

  @Test
  public void tEST4LISTING() {
    driver.get("http://localhost:8080//");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.findElement(By.linkText("Catalog")).click();
    {
      List<WebElement> elements = driver.findElements(By.id("cat-id1"));
      assert(elements.size() > 0);
    }
    {
      List<WebElement> elements = driver.findElements(By.id("cat-id2"));
      assert(elements.size() > 0);
    }
    {
      List<WebElement> elements = driver.findElements(By.id("cat-id3"));
      assert(elements.size() > 0);
    }
    vars.put("cat3", driver.findElement(By.xpath("//div/ul/li[3]")).getText());
    assertEquals(vars.get("cat3").toString(), "ID 3. Mistoffelees");
  }
  @Test
  public void tEST5RENTACAT() {
    driver.get("http://localhost:8080//");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.findElement(By.linkText("Rent-A-Cat")).click();
    {
      List<WebElement> elements = driver.findElements(By.xpath("//button[@onclick=\'rentSubmit()\']"));
      assert(elements.size() > 0);
    }
    {
      List<WebElement> elements = driver.findElements(By.xpath("//button[@onclick=\'returnSubmit()\']"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void tEST6RENT() {
    driver.get("http://localhost:8080//greet-a-cat");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.findElement(By.linkText("Rent-A-Cat")).click();
    driver.findElement(By.id("rentID")).click();
    driver.findElement(By.id("rentID")).sendKeys("1");
    driver.findElement(By.xpath("//button[contains(.,\'Rent\')]")).click();
    vars.put("id1result", driver.findElement(By.xpath("//div/ul/li[1]")).getText());
    vars.put("id2result", driver.findElement(By.xpath("//div/ul/li[2]")).getText());
    vars.put("id3result", driver.findElement(By.xpath("//div/ul/li[3]")).getText());
    vars.put("results", driver.findElement(By.id("rentResult")).getText());
    assertEquals(vars.get("id1result").toString(), "Rented out");
    assertEquals(vars.get("id2result").toString(), "ID 2. Old Deuteronomy");
    assertEquals(vars.get("id3result").toString(), "ID 3. Mistoffelees");
    assertEquals(vars.get("results").toString(), "Success!");
  }
  @Test
  public void tEST7RETURN() {
    driver.get("http://localhost:8080//");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.findElement(By.linkText("Rent-A-Cat")).click();
    driver.findElement(By.id("rentID")).click();
    driver.findElement(By.id("rentID")).sendKeys("2");
    driver.findElement(By.xpath("//button[contains(.,\'Rent\')]")).click();
    driver.findElement(By.id("returnID")).click();
    driver.findElement(By.id("returnID")).sendKeys("2");
    driver.findElement(By.xpath("//button[contains(.,\'Return\')]")).click();
    vars.put("c2", driver.findElement(By.id("cat-id2")).getText());
    vars.put("c3", driver.findElement(By.id("cat-id3")).getText());
    vars.put("c1", driver.findElement(By.id("cat-id1")).getText());
    vars.put("res", driver.findElement(By.id("returnResult")).getText());
    assertEquals(vars.get("c1").toString(), "ID 1. Jennyanydots");
    assertEquals(vars.get("c2").toString(), "ID 2. Old Deuteronomy");
    assertEquals(vars.get("c3").toString(), "ID 3. Mistoffelees");
    assertEquals(vars.get("res").toString(), "Success!");
  }
  @Test
  public void tEST8FEEDACAT() {
    driver.get("http://localhost:8080//");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.findElement(By.linkText("Feed-A-Cat")).click();
    {
      List<WebElement> elements = driver.findElements(By.xpath("/html/body/div/main/div[1]/div[2]/div[3]/button"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void tEST9FEED() {
    driver.get("http://localhost:8080//");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.findElement(By.linkText("Feed-A-Cat")).click();
    driver.findElement(By.id("catnips")).sendKeys("6");
    driver.findElement(By.xpath("//button[contains(.,\'Feed\')]")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
      wait.until(ExpectedConditions.textToBe(By.id("feedResult"), "Nom, nom, nom."));
    }
    vars.put("res", driver.findElement(By.id("feedResult")).getText());
    assertEquals(vars.get("res").toString(), "Nom, nom, nom.");
  }
  @Test
  public void tEST10GREETACAT() {
    driver.get("http://localhost:8080//");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.findElement(By.linkText("Greet-A-Cat")).click();
    vars.put("meow", driver.findElement(By.cssSelector("#greeting > h4")).getText());
    assertEquals(vars.get("meow").toString(), "Meow!Meow!Meow!");
  }
  @Test
  public void tEST11GREETACATWITHNAME() {
    driver.get("http://localhost:8080//greet-a-cat/Jennyanydots");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    vars.put("res", driver.findElement(By.cssSelector("#greeting > h4")).getText());
    assertEquals(vars.get("res").toString(), "Meow! from Jennyanydots.");
  }
  @Test
  public void dEFECT1FUNFEED() {
    driver.get("http://localhost:8080/");
    driver.manage().window().setSize(new Dimension(1680, 1025));
    driver.findElement(By.linkText("Feed-A-Cat")).click();
    driver.findElement(By.id("catnips")).click();
    driver.findElement(By.id("catnips")).sendKeys("-3");
    driver.findElement(By.cssSelector(".btn")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
      wait.until(ExpectedConditions.textToBe(By.id("feedResult"), "Cat fight!"));
    }
    assertThat(driver.findElement(By.id("feedResult")).getText(), is("Cat fight!"));
  }
  @Test
  public void dEFECT2GREETACAT() {
    driver.get("http://localhost:8080/");
    js.executeScript("document.cookie = \"1=true\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.manage().window().setSize(new Dimension(1680, 1025));
    driver.findElement(By.linkText("Greet-A-Cat")).click();
    assertThat(driver.findElement(By.xpath("//*[@id=\"greeting\"]/h4")).getText(), is("Meow!Meow!"));
  }
  @Test
  public void dEFECT3GREETACATWITHNAME() {
    driver.get("http://localhost:8080/");
    js.executeScript("document.cookie = \"1=true\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.get("http://localhost:8080/greet-a-cat/Jennyanydots");
    assertThat(driver.findElement(By.xpath("//*[@id=\"greeting\"]/h4")).getText(), is("Jennyanydots is not here."));
  }
}
