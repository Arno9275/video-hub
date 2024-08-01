package com.video.processor.util;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.video.common.core.constant.video.MyflixerzConstant;
import com.video.processor.model.vo.VideoInfoVO;
import com.video.processor.model.vo.myflixerz.VideoSourceUrl;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MyflixerzUtil {

   // private static ConcurrentHashMap<String, VideoInfoVO> videoInfoVOConcurrentHashMap = new ConcurrentHashMap<>();


    /**
     * 处理电影栏目页面
     *
     * @param sourcesIds     sourcesId列表
     * @param episodeListUrl episodeList URL列表
     * @param dataLinkIds    data-linkid列表
     * @param pageSize 开始页数
     */
    public static Map<String,VideoInfoVO> processMoviePage(List<String> sourcesIds, List<String> episodeListUrl,
                                         List<String> dataLinkIds,Integer pageSize) throws IOException {
        Map<String,VideoInfoVO> videoInfoVOMap = Maps.newHashMap();
        // 获取第一页电影栏目信息
        Document document = Jsoup.connect(MyflixerzConstant.MOVIE_PAGE_URL + pageSize).get();
        Elements pageLinks = document.select("a.page-link");
        Integer maxNum = 0;
        // 获取分页最大数
        for (Element link : pageLinks) {
            if (link.attr("title").equals("Last")) {
                String href = link.attr("href");
                Pattern pattern = Pattern.compile("page=(\\d+)$");
                Matcher matcher = pattern.matcher(href);
                if (matcher.find()) {
                    String number = matcher.group(1);
                    maxNum = Integer.valueOf(number);
                    break;
                }
            }
        }
        log.info("栏目:{},分页最大栏目数:{}", "movie", maxNum);

        Elements divs = document.select("div.flw-item");
        for (Element div : divs) {
            Element filmName = div.select(".film-name a").first();
            Element filmInfor = div.select(".fd-infor").first();

            String title = filmName.text(); // 获取电影标题值
            String sPath = filmName.attr("href"); // 获取链接值
            String sPathId = sPath.substring(sPath.lastIndexOf("-") + 1); // 从链接值中提取 ID
            String year = filmInfor.select(".fdi-item").first().text(); // 获取年份值


            Element aTag = div.select("a.film-poster-ahref").first();
            String href = aTag.attr("href");
            Pattern pattern = Pattern.compile("-(\\d+)$");
            Matcher matcher = pattern.matcher(href);
            if (matcher.find()) {
                sPathId = matcher.group(1);
            }

            // 打印每部电影的信息
            log.info("Title: {}, SPath: {}, SPathId: {}, Year: {}", title, sPath, sPathId, year);
            sourcesIds.add(sPathId);
            episodeListUrl.add(MyflixerzConstant.MOVIE_EPISODE_LIST_URL + sPathId);
            VideoInfoVO videoInfoVO = new VideoInfoVO();
            videoInfoVO.setTitle(title);
            videoInfoVO.setSPath(sPath);
            videoInfoVO.setSPathId(sPathId);
            videoInfoVO.setYear(year);

            // 解析HTML获取data-linkid
            Document episodeHtml = Jsoup.connect(MyflixerzConstant.MOVIE_EPISODE_LIST_URL + sPathId).get();
            Elements navItems = episodeHtml.getElementsByClass("nav-item");
            //一个navItems-视频下可能有多个播放方式路径
            List<VideoSourceUrl> videoSourceUrlList = new ArrayList<>();
            for (Element navItem : navItems) {
                String dataLinkid = navItem.select("a").attr("data-linkid");
                dataLinkIds.add(dataLinkid);
                VideoSourceUrl videoSourceUrl = fetchAndProcessSource(MyflixerzConstant.SOURCES_API_URL,dataLinkid);
                videoSourceUrl.setSPathId(sPathId);
                videoSourceUrlList.add(videoSourceUrl);
            }
            videoInfoVO.setVideoSourceUrlList(videoSourceUrlList);
            videoInfoVOMap.put(sPathId, videoInfoVO);
        }
        writeToFile(videoInfoVOMap);
        return videoInfoVOMap;
    }

    private static void writeToFile(Map<String, VideoInfoVO> videoInfoVOConcurrentHashMap) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MyflixerzConstant.FILE_WRITER_PATH))) {
            for (Map.Entry<String, VideoInfoVO> entry : videoInfoVOConcurrentHashMap.entrySet()) {
                String sPathId = entry.getKey();
                VideoInfoVO videoInfoVO = entry.getValue();
                for (VideoSourceUrl videoSourceUrl : videoInfoVO.getVideoSourceUrlList()) {
                    String line = sPathId + ";" + videoSourceUrl.getSourcesApiUrl();
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            log.error("视频获取路径写入文件失败 e:{}",e);
            throw new IOException();
        }
    }



    /**
     * 获取并处理源数据
     */
    private static VideoSourceUrl fetchAndProcessSource(String sourcesUrl,String sourcesId) {
        VideoSourceUrl sourceUrl = new VideoSourceUrl();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(sourcesUrl + sourcesId);
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
            String type = "";
            String link = "";
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject json = JSONObject.parseObject(responseBody);
                type = json.getString("type");
                link = json.getString("link");
                System.out.println("Type: " + type);
                System.out.println("Link: " + link);
                sourceUrl.setSourcesId(sourcesId);
                sourceUrl.setSourcesApiUrl(sourcesUrl + sourcesId);
                sourceUrl.setType(type);
                sourceUrl.setLink(link);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceUrl;
    }
}
