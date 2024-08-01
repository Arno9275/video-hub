package com.video.processor;

import com.video.common.core.utils.ApplicationUtil;
import com.video.common.core.utils.DefaultProfileUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@ComponentScan(value = "com.video.processor.*")
@SpringBootApplication
public class VideoProcessorApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(VideoProcessorApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        ApplicationUtil.startPrintInformation(env);
    }

}
