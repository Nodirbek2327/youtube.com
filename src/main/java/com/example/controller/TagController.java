package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.dto.TagDTO;
import com.example.service.TagService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = {"/create"})
    public ResponseEntity<?> create(@RequestBody TagDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(tagService.create(dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/update")
    public ResponseEntity<Boolean> update(@RequestBody TagDTO dto,
                                          @RequestParam("id") Integer id) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(tagService.update(id, dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id) {
        Boolean response = tagService.delete(id);
        if (response) {
            return ResponseEntity.ok("category deleted");
        }
        return ResponseEntity.badRequest().body("category Not Found");
    }

    @GetMapping(value = "/open/categories")
    public ResponseEntity<?> List() {
        return ResponseEntity.ok(tagService.List());
    }
}
