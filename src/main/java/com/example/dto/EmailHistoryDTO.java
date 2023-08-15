package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class EmailHistoryDTO {
    private String id;
    private String message;
    private String to_email;
    private String title;
    private LocalDateTime createdDate;
}
