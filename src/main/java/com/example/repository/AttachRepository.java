package com.example.repository;

import com.example.entity.AttachEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface AttachRepository extends CrudRepository<AttachEntity, String>, PagingAndSortingRepository<AttachEntity, String> {

    @Query("from AttachEntity where id = :id and extension != :ext")
    AttachEntity findByIdAndExtension(@Param("id") String attachId, @Param("ext") String ext);
}
