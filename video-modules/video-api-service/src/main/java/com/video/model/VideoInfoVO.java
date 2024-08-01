package com.video.model;

import com.video.model.myflixerz.VideoSourceUrl;
import lombok.Data;

import java.util.List;

@Data
public class VideoInfoVO {

    /**
     * 视频标题
     */
    private String title;

    /**
     * 视频年份
     */
    private String year;

    private String description;

    /**
     * 视频页路径地址:
     * myflixerz /movie/shakespeares-shitstorm-107395
     *
     *
     */
    private String sPath;

    /**
     * 视频路径id
     * myflixerz 107395
     */
    private String sPathId;

    private List<VideoSourceUrl> videoSourceUrlList;

}
