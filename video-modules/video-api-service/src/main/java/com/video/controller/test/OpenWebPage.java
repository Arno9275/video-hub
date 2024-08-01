package com.video.controller.test;

import java.awt.Desktop;
import java.net.URI;

public class OpenWebPage {
    public static void main(String[] args) {
        String url = "https://myflixerz.to/ajax/episode/sources/10372837"; // 指定要访问的网址

        try {
            // 检查当前平台是否支持 Desktop 功能
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    // 调用默认浏览器打开指定网址
                    desktop.browse(new URI(url));
                }
            } else {
                // 如果不支持 Desktop 功能，可以选择其他处理方式
                System.out.println("Desktop is not supported on this platform.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}