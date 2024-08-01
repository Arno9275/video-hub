package com.video.processor.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Video implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id; // 视频ID

    private Long categoryId; // 视频所属分类ID

    private String title; // 视频标题

    private String videoUrl; // 视频地址
}