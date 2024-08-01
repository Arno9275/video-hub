package com.video.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DocumentUtil {

    /**
     * 从 HTML 获取页面所有图片
     *
     * @throws IOException
     */
    public static void getPicByDocument() throws IOException {
        Document document = Jsoup.connect("https://www.baidu.com").get();
        // 获取HTML页面中的所有图片
        Elements images = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
        for (Element image : images) {
            String print = new StringBuilder()
                    .append("src : " + image.attr("src"))
                    .append("，height : " + image.attr("height"))
                    .append("，width : " + image.attr("width"))
                    .append("，alt : " + image.attr("alt"))
                    .toString();
            System.out.println(print);
        }
    }

    /**
     * 从HTML获取指定标签的内容
     *
     * @throws IOException
     */
    public static void getContentByHtml() throws IOException {

    }

    public static void main(String[] args) {
        // 直接加载百度连接，获取百度首页页面信息
//        Document document = Jsoup.connect("https://www.baidu.com").get();
//        System.out.println(document.toString());


        //通过字符串加载文档
        // 定义一个 html 页面的字符串信息
//        String html = "<html><head><title></title></head><body>Hello World</body></html>";
//        Document document = Jsoup.parse(html);
//        System.out.println(document.toString()


        //从 HTML 获取标题信息
//        // 直接加载百度连接，获取百度首页页面信息
//        Document document = Jsoup.connect("https://www.baidu.com").get();
//// 获取页面标题
//        System.out.println(document.title());


        //从 HTML 获取页面所有超链接
//        // 直接加载百度连接，获取百度首页页面信息
//        Document document = Jsoup.connect("https://www.baidu.com").get();
//// 获取HTML页面中的所有链接
//        Elements links = document.select("a[href]");
//        for (Element link : links) {
//            System.out.println("link : " + link.attr("href") + "，text : " + link.text());
//        }


        //从HTML获取指定标签的内容
//        String html = "<p><span>hello world</span></p>";
//        Document document = Jsoup.parse(html);
//        Elements elements = document.getElementsByTag("span");
//        System.out.println(elements.toString());


        //从 HTML 获取指定标签ID的内容
//        String html = "<p><span id=\"span111\">hello world</span></p>";
//        Document document = Jsoup.parse(html);
//        Element element = document.getElementById("span111");
//        System.out.println(element.toString());


        //从 HTML 获取指定标签 class 的内容
//        String html = "<p><span class=\"class111\">hello world</span></p>";
//        Document document = Jsoup.parse(html);
//        Elements elements = document.getElementsByClass("class111");
//        System.out.println(elements.toString());


        //从 HTML 获取指定标签属性的内容
//        String html = "<p><span datafld=\"11111\">hello world</span></p>";
//        Document document = Jsoup.parse(html);
//        Elements elements = document.getElementsByAttribute("datafld");
//        System.out.println(elements.first().attributes().html());


        //消除不信任的HTML(以防止XSS)
//        // 原始文件
//        String dirtyHTML = "<p><a href='http://www.baidu.com/' onclick='sendCookiesToMe()'>Link</a></p>";
//// 需要清理的标签
//        String cleanHTML = Jsoup.clean(dirtyHTML, Whitelist.basic());
//// 输出结果
//        System.out.println(cleanHTML);
    }
}
