package com.video.job.client.handler;

import com.video.api.VideoProcessorService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class VideoJobHandler {

    @Autowired
    private VideoProcessorService videoProcessorService;

    @XxlJob("myflixerzHandler")
    public void myflixerzHandler() {
        long startTime = System.currentTimeMillis();
        videoProcessorService.execute();
        log.info("【myflixerz定时任务end... 耗时：{} ms】", System.currentTimeMillis() - startTime);
    }
}
