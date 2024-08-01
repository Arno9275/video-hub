package com.video.processor.controller;

import com.video.common.core.domain.R;
import com.video.processor.service.MyflixerzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/processor")
public class VideoProcessorController {

    @Autowired
    private MyflixerzService myflixerzService;

    @GetMapping("/videoTempWriteToFileExecute")
    public R videoTempWriteToFileExecute() {
        myflixerzService.videoTempWriteToFile();
        return R.ok();
    }

    @GetMapping("execute")
    public R execute() {
        return R.ok();
    }
}
