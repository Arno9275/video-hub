package com.video.controller.video;


import com.video.common.core.domain.R;
import com.video.service.video.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;


    @GetMapping("getList")
    public R getList() {
        return R.ok(videoService.getList());
    }
}
