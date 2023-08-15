package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, String> {
    PageImpl<EmailHistoryEntity> findAll(Pageable pageable);
    PageImpl<EmailHistoryEntity> findAllByEmail(String email, Pageable pageable);
}
