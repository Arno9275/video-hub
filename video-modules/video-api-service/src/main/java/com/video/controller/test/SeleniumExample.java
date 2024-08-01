package com.video.controller.test;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.HttpCommandExecutor;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.http.HttpRequest;

public class SeleniumExample {

    private final static String driver = "webdriver.chrome.driver";
    private final static String chromeDriver = "D:\\Office\\chromedriver-win64\\chromedriver.exe";

    public static void main(String[] args) {
        System.out.println("打开浏览器进行操作");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
        options.addArguments("--remote-allow-origins=*");
     //   options.addArguments("--referer=" + "https://myflixerz.to/");
        options.addArguments("--name=selenium");
        System.setProperty(driver,chromeDriver);


        //获取控制 打开浏览器
        WebDriver driver = new ChromeDriver(options);

        driver.manage().window().maximize();//浏览器最大化
        //超时等待30秒
        Duration duration = Duration.ofSeconds(30);
        driver.manage().timeouts().implicitlyWait(duration);
        driver.get("https://megacloud.tv/embed-1/e-1/F43ZGYha5R1D?z=");

    }

}
