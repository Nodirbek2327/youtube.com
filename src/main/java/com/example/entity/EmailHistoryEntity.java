package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "email_history")
public class EmailHistoryEntity extends BaseStringEntity {
    private String message;
    private String email;
    private String title;
}
