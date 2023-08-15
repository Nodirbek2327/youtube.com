package com.example.dto;

import com.example.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ChannelDTO {
    private String id;
    private String name;
    private String attachId;
    private String description;
    private Status status;
    private String banner;
    private Integer profileId;
    private LocalDateTime createdDate;
}
