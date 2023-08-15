package com.example.mapper;

import com.example.entity.ChannelEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.PlaylistStatus;

public interface PlayListInfoMapper {
    Integer getId();
    String getName();
    String getDescription();
    PlaylistStatus getStatus();
    Integer getOrderNumber();
    String getCreatedDate();
    ChannelEntity getChannel();
    ProfileEntity getProfile();
}
