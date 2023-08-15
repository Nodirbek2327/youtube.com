package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class EmailHistoryFilterDTO {
    private String email;
    private LocalDate created_date_from;
    private LocalDate created_date_to;
}
