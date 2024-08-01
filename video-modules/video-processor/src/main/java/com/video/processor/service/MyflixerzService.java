package com.video.processor.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.video.common.core.constant.video.MyflixerzConstant;
import com.video.common.redis.service.RedisService;
import com.video.processor.mapper.VideoCaptureMyflixerzMapper;
import com.video.processor.model.entity.VideoCaptureMyflixerz;
import com.video.processor.model.vo.VideoInfoVO;
import com.video.processor.model.vo.VideoUrlVO;
import com.video.processor.model.vo.myflixerz.FileReadVideoUrlTemp;
import com.video.processor.util.MyflixerzUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 视频已抓取数据处理-myflixerz
 */
@Slf4j
@Service
public class MyflixerzService extends ServiceImpl<VideoCaptureMyflixerzMapper, VideoCaptureMyflixerz> {

    @Autowired
    private RedisService redisService;

    /**
     * 访问网站栏目页面将视频基础信息写入文本中供 数据抓取脚本 获取
     */
    public void videoTempWriteToFile() {
        List<String> sourcesIds = new ArrayList<>();
        List<String> episodeListUrl = new ArrayList<>();
        List<String> dataLinkIds = new ArrayList<>();
        //获取数据库已有页数

        Map<String, VideoInfoVO> videoInfoVOMap = null;
        //获取上次记录最新页数,无则从1开始
        LambdaQueryWrapper<VideoCaptureMyflixerz> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(VideoCaptureMyflixerz::getPageSize).last("limit 1")
                .select(VideoCaptureMyflixerz::getPageSize);
        VideoCaptureMyflixerz videoCaptureMyflixerz = this.baseMapper.selectOne(queryWrapper);
        Integer pageSize = Objects.isNull(videoCaptureMyflixerz) ? 1 : videoCaptureMyflixerz.getPageSize();

        //一次执行获取10页的数据
        for (int i = 1; i <= 10; i++) {
            try {
                final Long startTime = System.currentTimeMillis();
                videoInfoVOMap = MyflixerzUtil.processMoviePage(sourcesIds, episodeListUrl, dataLinkIds, pageSize);
                log.info("耗时统计(s):",System.currentTimeMillis() - startTime / 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Objects.nonNull(videoInfoVOMap) && videoInfoVOMap.size() > 0) {
                final Long nowTime = System.currentTimeMillis();
                List<VideoCaptureMyflixerz> videoCaptureMyflixerzList = new ArrayList<>();
                for (VideoInfoVO videoInfoVO : videoInfoVOMap.values()) {
                    videoCaptureMyflixerz = new VideoCaptureMyflixerz();
                    videoCaptureMyflixerz.setTitle(videoInfoVO.getTitle());
                    videoCaptureMyflixerz.setSPath(videoInfoVO.getSPath());
                    videoCaptureMyflixerz.setYear(Integer.valueOf(videoInfoVO.getYear()));
                    videoCaptureMyflixerz.setType(1);//电影
                    videoCaptureMyflixerz.setStatus(1);
                    videoCaptureMyflixerz.setCreateTime(nowTime);
                    videoCaptureMyflixerz.setUpdateTime(nowTime);
                    videoCaptureMyflixerz.setSPathId(Long.valueOf(videoInfoVO.getSPathId()));
                    videoCaptureMyflixerz.setPageSize(pageSize);
                    videoCaptureMyflixerzList.add(videoCaptureMyflixerz);
                }
                if (videoCaptureMyflixerzList.size() > 0) {
                    int rs = this.baseMapper.saveBatch(videoCaptureMyflixerzList);
                }
                pageSize++;
            }
        }

    }

    public void execute() {

    }

    /**
     * 初始化文件内容
     *
     * @return
     */
    public static Boolean initFile() {
        final String filePath = MyflixerzConstant.FILE_WRITER_PATH;
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(""); // Clearing the file content
            fileWriter.close();
        } catch (IOException e) {
            log.error("myflixerz-初始化文件内容失败,异常:", e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 从文件中获取已抓取的视频数据
     *
     * @return 已抓取的视频数据列表
     */
    public static List<FileReadVideoUrlTemp> readVideoFile() {
        String filePath = "E:\\cc\\视频\\脚本\\网站一\\视频地址\\video_url.txt";

        try {
            return Files.lines(Paths.get(filePath))
                    // 检查每一行是否以 ".m3u8" 结尾，根据结尾情况处理
                    .map(line -> line.endsWith(".m3u8") ? line + "," : line)
                    // 将处理后的内容连接成一个字符串
                    .collect(Collectors.collectingAndThen(Collectors.joining(),
                            content -> parseFileContent(content)));
        } catch (IOException e) {
            System.err.println("发生错误: " + e.getMessage());
        }
        return new ArrayList<>();
    }


    /**
     * 解析文件内容并生成视频数据
     *
     * @param fileContent 文件内容
     * @return 包含视频数据的列表
     */
    public static List<FileReadVideoUrlTemp> parseFileContent(String fileContent) {
        List<FileReadVideoUrlTemp> videoList = new ArrayList<>();
        // 匹配视频源的正则表达式
        Pattern sourcePattern = Pattern.compile("https?://([^/]+)");

        // 按分号分割每一行数据
        for (String line : fileContent.split(";")) {
            if (line.trim().isEmpty()) {
                continue; // 跳过空行
            }
            FileReadVideoUrlTemp currentVideo = new FileReadVideoUrlTemp();
            List<VideoUrlVO> videoUrlVOList = new ArrayList<>();

            // 按逗号分割每段视频数据
            for (String part : line.split(",")) {
                if (part.matches("\\d+")) {
                    // 如果是纯数字，则设置为sPathId
                    currentVideo.setSPathId(Long.parseLong(part.trim()));
                } else {
                    VideoUrlVO videoUrlVO = new VideoUrlVO();
                    String videoUrlString = part.trim();
                    Matcher sourceMatcher = sourcePattern.matcher(videoUrlString);
                    if (sourceMatcher.find()) {
                        // 提取视频源
                        videoUrlVO.setSource(sourceMatcher.group(1));
                    }
                    // 设置视频URL
                    videoUrlVO.setVideoUrl(videoUrlString);
                    videoUrlVOList.add(videoUrlVO);
                }
            }
            // 设置视频URL列表
            currentVideo.setVideoUrlVOList(videoUrlVOList);
            videoList.add(currentVideo);
        }

        return videoList;
    }

    /**
     * 更新视频db抓取记录状态
     *
     * @return
     */
    public Boolean updateCaptureStatus() {
        return true;
    }

    public static void main(String[] args) {
        int i = 1;
        i++;
        System.out.println(i);
    }
}
