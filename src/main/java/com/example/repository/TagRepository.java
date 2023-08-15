package com.example.repository;

import com.example.entity.TagEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends CrudRepository<TagEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible = false where id =:id")
    int delete(@Param("id") Integer id);
}
