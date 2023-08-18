package com.example.controller;

import com.example.dto.EmailHistoryDTO;
import com.example.dto.EmailHistoryFilterDTO;
import com.example.service.EmailHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/email_history")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to) {
        log.info("pagination email history");
        return ResponseEntity.ok(emailHistoryService.emailHistoryPagination(from-1, to));
    }

    @GetMapping(value = "/open/pagination/{email}")
    public ResponseEntity<?> paginationByEmail(@RequestParam("from") int from,
                                        @RequestParam("to") int to,
                                        @PathVariable("email") String email) {
        log.info("pagination email_history by  email: {} "+email);
        return ResponseEntity.ok(emailHistoryService.emailHistoryPaginationByEmail(from-1, to, email));
    }

    @PostMapping(value = "/open/filter")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> filter(@RequestBody EmailHistoryFilterDTO filterDTO,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "2") int size) {
        log.info("filtering  email history");
        PageImpl<EmailHistoryDTO> response = emailHistoryService.filter(filterDTO, page - 1, size);
        return ResponseEntity.ok(response);
    }


}
