package com.video.job.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@ComponentScan(value = "com.video.*")
@EnableFeignClients(basePackages = "com.video.api")
public class JobClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobClientApplication.class,args); //启动服务
    }

}
