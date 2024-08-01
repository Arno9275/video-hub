package com.video.processor.model.vo.myflixerz;

import com.video.processor.model.vo.VideoUrlVO;
import lombok.Data;

import java.util.List;

/**
 * 文件读取数据临时存储
 */
@Data
public class FileReadVideoUrlTemp {

    /**
     * 视频id
     */
    private Long sPathId;

    /**
     * 存储不同的视频地址
     */
    private List<VideoUrlVO> videoUrlVOList;
}
