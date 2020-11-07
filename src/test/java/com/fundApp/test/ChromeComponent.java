package com.fundApp.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeComponent {

    private WebDriver chrome;

    public ChromeComponent() {
        System.setProperty("webdriver.chrome.driver", "db/chromedriver");

        ChromeOptions options = new ChromeOptions();
        chrome = new ChromeDriver(options);
    }

    public void get(String url) {
        chrome.get(url);
    }

    public void refresh() {

        chrome.navigate().refresh();
    }

    public String html() {

        return chrome.getPageSource();
    }

    public void quit() {
        chrome.quit();
    }

}
