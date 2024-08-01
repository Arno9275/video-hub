package com.video.service.video;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.video.mapper.VideoMapper;
import com.video.model.entity.Video;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService extends ServiceImpl<VideoMapper, Video> {


    public List<Video> getList() {
        return this.list();
    }
}
