package com.example.dto;

import com.example.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtDTO {
    private Integer id;
    private ProfileRole role;
    private String email;

    public JwtDTO(Integer id, ProfileRole role) {
        this.id = id;
        this.role = role;
    }


    public JwtDTO(String email, ProfileRole role) {
        this.email = email;
        this.role = role;
    }
}
