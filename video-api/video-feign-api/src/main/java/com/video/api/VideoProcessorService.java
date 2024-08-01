package com.video.api;

import com.video.api.factory.VideoProcessorFallbackFactory;
import com.video.common.core.constant.ServiceNameConstants;
import com.video.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 视频数据源清洗服务
 * 
 * @author video
 */
@FeignClient(contextId = "videoProcessorService", value = ServiceNameConstants.VIDEO_PROCESSOR_SERVICE, fallbackFactory = VideoProcessorFallbackFactory.class)
public interface VideoProcessorService {

    @GetMapping("/processor/execute")
    public R execute();

}
