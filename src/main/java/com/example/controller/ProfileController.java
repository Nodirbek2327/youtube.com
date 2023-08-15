package com.example.controller;


import com.example.config.CustomUserDetails;
import com.example.dto.ProfileDTO;
import com.example.service.ProfileService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = {"/create"})
    public ResponseEntity<?> create(@RequestBody ProfileDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(profileService.create(dto, userDetails.getProfile().getId()));
    }

    @PostMapping(value = {"/password"})
    public ResponseEntity<?> changePassword(@RequestParam("new") String newPassword) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(profileService.changePassword(newPassword, userDetails.getProfile().getId()));
    }

//    @PutMapping(value = "/{id}")
//    public ResponseEntity<Boolean> updateEmail(@RequestBody ProfileDTO dto,
//                                          @PathVariable("id") Integer id) {
//        return ResponseEntity.ok(profileService.update(id, dto));
//    }

    @PutMapping(value = "/update_detail")
    public ResponseEntity<Boolean> updateDetail(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.updateDetail(SpringSecurityUtil.getCurrentUsername(), dto));
    }


    @PutMapping(value = "/update/attach")
    public ResponseEntity<Boolean> updateAttach(@RequestParam("attach_id") String attach_id) {
        return ResponseEntity.ok(profileService.updateAttach(SpringSecurityUtil.getCurrentUser().getProfile(), attach_id));
    }

    @GetMapping(value = "/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(profileService.getProfile(SpringSecurityUtil.getCurrentUser().getProfile().getId()));
    }


}
