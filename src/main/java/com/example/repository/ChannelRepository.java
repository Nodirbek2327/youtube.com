package com.example.repository;

import com.example.entity.ChannelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends CrudRepository<ChannelEntity, String> {
    Optional<ChannelEntity> findByName(String name);

    @Query("update ChannelEntity set attachId = :new where profileId = :prtId ")
    int changeAttach(@Param("new") String attachId, @Param("prtId")  Integer id);

    @Query("update ChannelEntity set bannerId = :new where profileId = :prtId ")
    int changeBanner(@Param("new") String bannerId, @Param("prtId")  Integer id);

    Page<ChannelEntity> findAllByStatus(String status, Pageable pageable);

    @Query("update ChannelEntity set status = 'BLOCK' where id = :id ")
    int changeStatus(@Param("id") String channelId);

    List<ChannelEntity> findAllByProfileId(Integer id);
}
