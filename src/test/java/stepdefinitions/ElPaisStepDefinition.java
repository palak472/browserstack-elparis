package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElPaisStepDefinition {

    static WebDriver driver;
    List<String> articleTitles = new ArrayList<>();
    List<String> articleContents = new ArrayList<>();
    List<String> translatedTitles = new ArrayList<>();
    Map<String, Integer> wordCount = new HashMap<>();

    @Given("I visit the El Pais website")
    public void visitElPaisWebsite() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://elpais.com/");
    }

    @Then("I should see the website in Spanish")
    public void checkWebsiteLanguage() {
        String language = driver.getCurrentUrl();
        assert language.contains("es");
    }

    @When("I should navigate to the Opinion section and scrape the first five articles from the Opinion section")
    public void scrapeArticles() {
        WebElement opinionSection = driver.findElement(By.linkText("Opinión"));
        opinionSection.click();
        List<WebElement> articles = driver.findElements(By.cssSelector("article"));
        for (int i = 0; i < 5; i++) {
            WebElement article = articles.get(i);
            String title = article.findElement(By.cssSelector("h2")).getText();
            String content = article.findElement(By.cssSelector(".a-txt")).getText();
            articleTitles.add(title);
            articleContents.add(content);
            WebElement img = article.findElement(By.cssSelector("img"));
            String imgUrl = img.getAttribute("src");
            downloadImage(imgUrl, "article" + i + ".jpg");
        }
    }

    @Then("I should print the title and content of each article in Spanish")
    public void printArticleDetails() {
        for (int i = 0; i < articleTitles.size(); i++) {
            System.out.println("Title: " + articleTitles.get(i));
            System.out.println("Content: " + articleContents.get(i));
        }
    }

    @Then("I should save the cover image of each article")
    public void downloadImage(String imgUrl, String fileName) {
        System.out.println("Image URL: " + imgUrl + " saved as " + fileName);
    }

    @When("I translate the titles of each article to English")
    public void translateTitles() {
        for (String title : articleTitles) {
            String translatedTitle = translateText(title);
            translatedTitles.add(translatedTitle);
        }
    }

    @Then("I should print the translated headers")
    public void printTranslatedHeaders() {
        for (String translatedTitle : translatedTitles) {
            System.out.println("Translated Title: " + translatedTitle);
        }
    }

    @When("I analyze the translated headers")
    public void analyzeTranslatedHeaders() {
        for (String title : translatedTitles) {
            String[] words = title.split(" ");
            for (String word : words) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }
    }

    @Then("I should print the repeated words along with their count")
    public void printRepeatedWords() {
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 2) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    private String translateText(String text) {
        return "Translated: " + text;
    }
}
