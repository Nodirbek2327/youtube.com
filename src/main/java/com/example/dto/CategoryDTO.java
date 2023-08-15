package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CategoryDTO {
    private Integer id;
    @NotBlank(message = "Name is required")
    private String name;
    private LocalDateTime createdDate;
}
