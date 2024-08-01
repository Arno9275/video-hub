package com.video.api.factory;

import com.video.api.VideoProcessorService;
import com.video.common.core.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * 降级处理
 */
@Component
public class VideoProcessorFallbackFactory implements FallbackFactory<VideoProcessorService> {
    private static final Logger log = LoggerFactory.getLogger(VideoProcessorFallbackFactory.class);

    @Override
    public VideoProcessorService create(Throwable throwable) {
        log.error("视频源处理服务调用失败:{}", throwable.getMessage());
        return new VideoProcessorService() {
            @Override
            public R execute() {
                return R.fail("视频源处理服务调用失败:" + throwable.getMessage());
            }
        };

    }
}
