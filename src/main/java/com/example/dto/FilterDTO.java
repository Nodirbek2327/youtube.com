package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class FilterDTO<T> {
    private List<T> entityList;
    private Long totalCount;

    public FilterDTO(List<T> entityList, Long totalCount) {
        this.entityList = entityList;
        this.totalCount = totalCount;
    }
}
