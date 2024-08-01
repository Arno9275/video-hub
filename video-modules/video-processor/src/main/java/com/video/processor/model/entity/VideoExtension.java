package com.video.processor.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class VideoExtension implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId("video_id")
    private Long videoId; // 视频表ID

    private String iconUrl; // 图标URL地址

    private Long videoDate; // 视频日期

    private String description; // 视频简介文本
}