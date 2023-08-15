package com.example.controller;

import com.example.dto.EmailHistoryDTO;
import com.example.dto.EmailHistoryFilterDTO;
import com.example.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email_history")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to) {

        return ResponseEntity.ok(emailHistoryService.emailHistoryPagination(from-1, to));
    }

    @GetMapping(value = "/open/pagination/{email}")
    public ResponseEntity<?> paginationByEmail(@RequestParam("from") int from,
                                        @RequestParam("to") int to,
                                        @PathVariable("email") String email) {
        return ResponseEntity.ok(emailHistoryService.emailHistoryPaginationByEmail(from-1, to, email));
    }

    @PostMapping(value = "/open/filter")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> filter(@RequestBody EmailHistoryFilterDTO filterDTO,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "2") int size) {
        PageImpl<EmailHistoryDTO> response = emailHistoryService.filter(filterDTO, page - 1, size);
        return ResponseEntity.ok(response);
    }


}
