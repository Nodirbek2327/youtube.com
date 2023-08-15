package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    private String id;  /* @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")........@GeneratedValue(strategy = GenerationType.UUID)*/
    @Column(name = "original_name")
    private String originalName;
    private String path;
    private Long size;
    private String extension;
    private Long  duration;
    private Integer prtId;
    @Column(name = "created_date")
    private LocalDateTime createdData=LocalDateTime.now();
    private String url;
}
