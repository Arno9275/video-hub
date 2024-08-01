package com.video.processor.model.vo.myflixerz;

import lombok.Data;

/**
 * 			https://myflixerz.to/ajax/episode/sources/10277344
 *                        {
 * 			"type": "iframe",
 * 			"link": "https://megacloud.tv/embed-1/e-1/XwtlUlaYEkWU?z="
 *            }
 */
@Data
public class VideoSourceUrl {

    /**
     * 107395
     * https://myflixerz.to/ajax/episode/list/107395
     */
    private String sPathId;

    /**
     * 10375423
     * /ajax/episode/sources/10375423
     */
    private String sourcesId;

    private String type;

    private String link;

    private String sourcesApiUrl;
}
