package com.example.service;

import com.example.dto.EmailHistoryDTO;
import com.example.dto.EmailHistoryFilterDTO;
import com.example.dto.FilterDTO;
import com.example.entity.EmailHistoryEntity;
import com.example.exp.ItemNotFoundException;
import com.example.repository.CustomRepository;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    @Autowired
    private CustomRepository customRepository;

    public PageImpl<EmailHistoryDTO> emailHistoryPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        PageImpl<EmailHistoryEntity> pageObj = emailHistoryRepository.findAll(pageable);
        return new PageImpl<>(getEmailHistoryDTOS(pageObj.getContent()), pageable, pageObj.getTotalElements());
    }

    public PageImpl<EmailHistoryDTO> emailHistoryPaginationByEmail(int page, int size, String email) {
        Pageable pageable = PageRequest.of(page, size);
        PageImpl<EmailHistoryEntity> pageObj = emailHistoryRepository.findAllByEmail(email, pageable);
        return new PageImpl<>(getEmailHistoryDTOS(pageObj.getContent()), pageable, pageObj.getTotalElements());
    }

    public PageImpl<EmailHistoryDTO> filter(EmailHistoryFilterDTO filterDTO, int page, int size) {
        FilterDTO<EmailHistoryEntity> result = customRepository.filterEmail(filterDTO, page, size);
        return new PageImpl<>(getEmailHistoryDTOS(result.getEntityList()), PageRequest.of(page, size), result.getTotalCount());
    }

    private EmailHistoryDTO toDTO(EmailHistoryEntity entity){
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setTo_email(entity.getTitle());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setTo_email(entity.getEmail());
        return dto;
    }


    private List<EmailHistoryDTO> getEmailHistoryDTOS(List<EmailHistoryEntity> list) {
        if (list.isEmpty()) {
            throw new ItemNotFoundException("email not found");
        }
        List<EmailHistoryDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }
}

