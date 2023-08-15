package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.dto.CategoryDTO;
import com.example.service.CategoryService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = {"/create"})
    public ResponseEntity<?> create(@RequestBody CategoryDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(categoryService.create(dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/update")
    public ResponseEntity<Boolean> update(@RequestBody CategoryDTO dto,
                                          @RequestParam("id") Integer id) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(categoryService.update(id, dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id) {
        Boolean response = categoryService.delete(id);
        if (response) {
            return ResponseEntity.ok("category deleted");
        }
        return ResponseEntity.badRequest().body("category Not Found");
    }

    @GetMapping(value = "/open/categories")
    public ResponseEntity<?> List() {
        return ResponseEntity.ok(categoryService.List());
    }
}
