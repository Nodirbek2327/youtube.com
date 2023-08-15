package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tag")
public class TagEntity extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "prt_id")
    private Integer prtId;
}
