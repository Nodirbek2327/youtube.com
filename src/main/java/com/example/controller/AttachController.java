package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.dto.AttachDTO;
import com.example.service.AttachService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

 //   private Logger log = LoggerFactory.getLogger(AuthController.class);
    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok().body(attachService.save(file, userDetails.getProfile().getId()));
    }

    @GetMapping(value = "/open/get/{id}", produces = MediaType.ALL_VALUE)
    public byte[] openByIdGeneral(@PathVariable("id") String id) {
        return attachService.loadByIdGeneral(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") String id) {
        return ResponseEntity.ok(attachService.delete(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to) {
        return ResponseEntity.ok(attachService.attachPagination(from-1, to));
    }

    @GetMapping("/open/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") String id) {
        return attachService.download(id);
    }

}
