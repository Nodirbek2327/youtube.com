package com.example.controller;


import com.example.config.CustomUserDetails;
import com.example.dto.PlaylistDTO;
import com.example.enums.PlaylistStatus;
import com.example.service.PlaylistService;
import com.example.util.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api/v1/play_list")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = {"/create"})
    public ResponseEntity<?> create(@RequestBody PlaylistDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        log.info("creating playlist");
        return ResponseEntity.ok(playlistService.create(dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody PlaylistDTO dto,
                                    @PathVariable("id") Integer id) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        log.info("updating playlist");
        return ResponseEntity.ok(playlistService.update(id, dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PutMapping(value = "/change_status")
    public ResponseEntity<Boolean> changeStatus(@RequestParam("plId") Integer plId,
                                                @RequestParam("status")  PlaylistStatus status) {
        log.info("changing playlist status");
        return ResponseEntity.ok(playlistService.changeStatus(status, plId, SpringSecurityUtil.getCurrentUser().getProfile().getId()));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @PutMapping(value = "/delete")
    public ResponseEntity<Boolean> delete(@RequestParam("plId") Integer plId) {
        log.info("deleting playlist id: {}"+plId);
        return ResponseEntity.ok(playlistService.delete(plId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam ("to") int to) {
        log.info("getting pagination playlist");
        return ResponseEntity.ok(playlistService.pagination(from-1, to));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/list")
    public ResponseEntity<?> list() {
        log.info("getting playlist all list");
        return ResponseEntity.ok(playlistService.list(SpringSecurityUtil.getCurrentUser().getProfile().getId()));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/user_list")
    public ResponseEntity<?> userPlayList() {
        log.info("getting All user Playlist");
        return ResponseEntity.ok(playlistService.userPlayList(SpringSecurityUtil.getCurrentUser().getProfile().getId()));
    }

    @GetMapping(value = "/list_channel")
    public ResponseEntity<?> listByChannelId(@RequestParam("channelId") String channelId) {
        log.info("getting All Playlist by channelId");
        return ResponseEntity.ok(playlistService.listByChannel(channelId));
    }


    @GetMapping(value = "/list_id")
    public ResponseEntity<?> listById(@RequestParam("id") Integer id) {
        log.info("getting All  Playlist by id");
        return ResponseEntity.ok(playlistService.listById(id));
    }


}
