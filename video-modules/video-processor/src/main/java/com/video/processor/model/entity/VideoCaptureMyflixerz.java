package com.video.processor.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;

@Data
public class VideoCaptureMyflixerz implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id; // 分类ID

    private String title;

    private String sPath;

    private Integer year;

    private Integer type;

    private Integer status;

    private Long createTime;

    private Long updateTime;

    private Long sPathId;

    private String anjianUrl;

    private Integer pageSize;
}