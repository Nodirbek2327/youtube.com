package com.example.dto;

import com.example.enums.PlaylistStatus;
import com.example.enums.VideoType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class VideoDTO {
    private String id;
    private String preview_attach_id;
    private String title;
    private Integer category_id;
    private String attach_id;
    private LocalDateTime created_date;
    private LocalDateTime published_date;
    private PlaylistStatus status;
    private VideoType type;
    private Integer view_count;
    private Integer shared_count;
    private String description;
    private String channel_id;
    private Integer like_count;
    private Integer dislike_count;
}
