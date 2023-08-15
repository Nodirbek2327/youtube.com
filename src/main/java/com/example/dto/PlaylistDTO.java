package com.example.dto;

import com.example.enums.PlaylistStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PlaylistDTO {
    private Integer id;
    private String channel_id;
    private String name;
    private String description;
    private PlaylistStatus status;
    private Integer orderNumber;
    private LocalDateTime createdDate;
}
