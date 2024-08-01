package com.video.controller.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileMain {

    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("E:\\cc\\视频\\脚本\\网站一\\视频地址\\video.txt"))) {
            String line = "测试";
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
