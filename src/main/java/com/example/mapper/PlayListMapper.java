package com.example.mapper;

import java.time.LocalDateTime;

public interface PlayListMapper {
    Integer getId();
    String getName();
    Integer getVideoCount();
    Integer getTotal_view_count();
    LocalDateTime getLast_update_date();
}
