package com.video.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/springBoot")
public class TestController {
    @RequestMapping("demo")
    public String demo() {
        return "Hello SpringBoot!";
    }
}