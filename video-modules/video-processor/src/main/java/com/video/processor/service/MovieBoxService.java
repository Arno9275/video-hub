package com.video.processor.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MovieBoxService {
    private final static String moviesIndexUrl = "https://www.movieboxpro.app/movie"; //电影栏目访问url
    private final static int initPageSize = 1; //分页初始页数

    public static void main(String[] args) throws IOException {
        // 获取第一页电影栏目信息
        String url = "https://www.movieboxpro.app/movie";
        String cookies = "ci=165e95e1920105; ui=eyJhcHBfa2V5IjoiNjQwNWMyZTYwNDVmNjU5YjdmZGNkOTU3ZmU1MmZlOWEiLCJlbmNyeXB0X2RhdGEiOiI4SGFFN25WMkQ2Ymk4c2xUeUxZT3BCbkRHYkdUclVKR29CU01SUWRMZVNNbjljWFVFd1Z4eUxvUW0ybUZVSEpWMXRyc1lZU1g1SE9tVGZoYUZRNzJzTGVOODFDSFZwUWsiLCJ2ZXJpZnkiOiIxYTY5Mzg5MTkxZmRjZDU5NDhjNTk0MjFjNDQ0MjliMyJ9; _ga=GA1.1.1886562323.1709792802; __stripe_mid=e04c72c4-b959-4dc4-b9d2-bd4c685d0ccd66d876; PHPSESSID=u0a5i4kvivpglojt6t823hhjfo; cf_clearance=dCI2YAXxA7htDzoPVawKWm8a0FQ9xfT8yM.QzN4Nqso-1714097784-1.0.1.1-JpPNfE5ogYor9I117RmDI0pJAV8X9yv7b8HWae.cAQwAjvB.IDd59ifUSGriCzFVgAx7jxMEqXpeBxPrkJBpwg; _ga_SSBQC5HB90=GS1.1.1714097784.7.1.1714098800.0.0.0";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36";

        // 创建连接，并添加请求头和Cookie
        Connection.Response response = Jsoup.connect(url)
                .header("Host", "www.movieboxpro.app")
                .header("Origin","https://www.movieboxpro.app")
                .header("User-Agent", userAgent)
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate, br, zstd")
                .header("Connection","keep-alive")
                .header("DNT","1")
                .header("Cookie", cookies)
                .method(Connection.Method.GET)
                .execute();

        // 获取包含Cookie的Document
        Document document = response.parse();

        System.out.println(document);

        Elements movieElements = document.select("div.movies > a");

        for (Element movieElement : movieElements) {
            String title = movieElement.attr("title");
            String movieUrl = movieElement.attr("href");

            Element imgElement = movieElement.selectFirst("img.icon");
            String iconUrl = imgElement.attr("src");

            double score = Double.parseDouble(movieElement.selectFirst("p.score > span").text());

            String freshnessIcon = movieElement.selectFirst("p.tomato > img").attr("src");
            int freshnessPercentage = Integer.parseInt(movieElement.selectFirst("p.tomato > span").text().replace("%", ""));

            String movieDetails = movieElement.selectFirst("div").text();

            System.out.println("电影标题: " + title);
            System.out.println("URL: " + movieUrl);
            System.out.println("图标URL: " + iconUrl);
            System.out.println("评分: " + score);
            System.out.println("新鲜度图标: " + freshnessIcon);
            System.out.println("新鲜度百分比: " + freshnessPercentage);
            System.out.println("详情: " + movieDetails);
            System.out.println("-----------------------------------");
        }
    }
}
