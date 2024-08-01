package com.video.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class VideoCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id; // 分类ID

    private String name; // 类型名称

    private String description; // 中文描述
}