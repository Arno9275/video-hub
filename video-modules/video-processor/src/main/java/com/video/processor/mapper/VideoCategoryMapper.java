package com.video.processor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.processor.model.entity.VideoCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoCategoryMapper extends BaseMapper<VideoCategory> {
}
