package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.dto.ChannelDTO;
import com.example.service.ChannelService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value = {"/create"})
    public ResponseEntity<?> create(@RequestBody ChannelDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(channelService.create(dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER')")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody ChannelDTO dto,
                                    @PathVariable("id") String id) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(channelService.update(id, dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER')")
    @PutMapping(value = "/update/attach")
    public ResponseEntity<Boolean> updateChannelPhoto(@RequestParam("attach_id") String attach_id) {
        return ResponseEntity.ok(channelService.updateAttach(SpringSecurityUtil.getCurrentUser().getProfile(), attach_id));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER')")
    @PutMapping(value = "/update/banner")
    public ResponseEntity<Boolean> updateChannelBanner(@RequestParam("attach_id") String attach_id) {
        return ResponseEntity.ok(channelService.updateBanner(SpringSecurityUtil.getCurrentUser().getProfile(), attach_id));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to) {
        return ResponseEntity.ok(channelService.pagination(from-1, to));
    }

    @GetMapping(value = "/open/get")
    public ResponseEntity<?> get(@RequestParam("channelId")  String channelId) {
        return ResponseEntity.ok(channelService.getChannel(channelId));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER', 'ROLE_ADMIN')")
    @PutMapping(value = "/change_status")
    public ResponseEntity<Boolean> changeStatus(@RequestParam("channelId") String channelId) {
        return ResponseEntity.ok(channelService.changeStatus(channelId));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(channelService.list(SpringSecurityUtil.getCurrentUser().getProfile().getId()));
    }

}
