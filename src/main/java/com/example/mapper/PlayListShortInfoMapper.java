package com.example.mapper;

import com.example.entity.ChannelEntity;
import com.example.entity.VideoEntity;

import java.util.List;


public interface PlayListShortInfoMapper {
     Integer getId();
     String getName();
     String getCreatedDate();
     ChannelEntity getChannel();
     Integer getVideoCount();
     List<VideoEntity> getVideoList();
}
