package com.video.processor.video;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.video.processor.mapper.VideoMapper;
import com.video.processor.model.entity.Video;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService extends ServiceImpl<VideoMapper, Video> {


    public List<Video> getList() {
        return this.list();
    }
}
