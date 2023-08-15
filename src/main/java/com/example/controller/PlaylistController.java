package com.example.controller;


import com.example.config.CustomUserDetails;
import com.example.dto.PlaylistDTO;
import com.example.service.PlaylistService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/play_list")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = {"/create"})
    public ResponseEntity<?> create(@RequestBody PlaylistDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(playlistService.create(dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER')")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody PlaylistDTO dto,
                                    @PathVariable("id") Integer id) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(playlistService.update(id, dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER')")
    @PutMapping(value = "/change_status")
    public ResponseEntity<Boolean> changeStatus(@RequestParam("plId") Integer plId) {
        return ResponseEntity.ok(playlistService.changeStatus(plId));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER', 'ROLE_ADMIN')")
    @PutMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam("plId") Integer plId) {
        return ResponseEntity.ok(playlistService.delete(plId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to) {
        return ResponseEntity.ok(playlistService.pagination(from-1, to));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(playlistService.list(SpringSecurityUtil.getCurrentUser().getProfile().getId()));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/user_list")
    public ResponseEntity<?> userPlayList() {
        return ResponseEntity.ok(playlistService.userPlayList(SpringSecurityUtil.getCurrentUser().getProfile().getId()));
    }

    @GetMapping(value = "/list_channel")
    public ResponseEntity<?> listByChannelId(@RequestParam("channelId") String channelId) {
        return ResponseEntity.ok(playlistService.listByChannel(channelId));
    }


    @GetMapping(value = "/list_id")
    public ResponseEntity<?> listByChannelId(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(playlistService.listById(id));
    }


}
