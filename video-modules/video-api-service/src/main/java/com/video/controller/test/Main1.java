package com.video.controller.test;

import com.alibaba.fastjson2.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main1 {

    private static final String chromeDriverPath = "D:\\Office\\chromedriver-win64\\chromedriver.exe";


    public static void main(String[] args) {
        final String url = "https://megacloud.tv/embed-1/e-1/UJGDYyzYwScj?z=";
        WebDriver driver = getChromeDriver(url);
        driver.get(url);

        // 获取页面返回的 JSON 数据
        String jsonData = driver.findElement(By.tagName("body")).getText();
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        String link = jsonObject.getString("link");
        // 获取当前页面的 URL
        String currentUrl = driver.getCurrentUrl();
        // 拼接新的链接地址
        String newUrl = currentUrl.substring(0, currentUrl.lastIndexOf("/")) + "/" + link;

        // 在当前页面打开新链接
        WebDriver newTabDriver = getChromeDriver("https://myflixerz.to/");
        newTabDriver.get(newUrl);
    }


    public static WebDriver getChromeDriver(String referer) {
        // 设置 Chrome 浏览器驱动路径
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        // 创建 Chrome 浏览器选项，模拟为谷歌浏览器
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--Accept=*/*");
        options.addArguments("--user-agent=" + "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
        options.addArguments("--referer=" + referer);
        options.addArguments("--remote-allow-origins=*");


        // 初始化 Chrome 浏览器
        WebDriver driver = new ChromeDriver(options);
        // 使用 Javascript 设置请求头 Referer
        ((JavascriptExecutor) driver).executeScript(
                "Object.defineProperty(navigator, 'referrer', {get: function(){return '" + referer + "';}});"
        );
        return driver;
    }
}