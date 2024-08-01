package com.video.processor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.processor.model.entity.VideoCaptureMyflixerz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoCaptureMyflixerzMapper extends BaseMapper<VideoCaptureMyflixerz> {

    int saveBatch(@Param("list") List<VideoCaptureMyflixerz> videoCaptureMyflixerzList);
}
