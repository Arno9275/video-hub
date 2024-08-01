package com.video.job.client.domain.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 三方视频网站基础信息枚举类
 */

@Getter
public enum ThirdPartyVideoSiteEnum {
    MYFLIXERZ("myflixerz","https://myflixerz.to/movie?page=1","https://myflixerz.to/ajax/episode/sources/","https://myflixerz.to/ajax/episode/list/"),;

    private String webName;
    private String v_1;
    private String v_2;
    private String v_3;


    ThirdPartyVideoSiteEnum(String webName, String v_1, String v_2, String v_3) {
        this.webName = webName;
        this.v_1 = v_1;
        this.v_2 = v_2;
        this.v_3 = v_3;
    }
}
