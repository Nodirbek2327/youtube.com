package com.example.repository;

import com.example.entity.ChannelEntity;
import com.example.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends CrudRepository<ChannelEntity, String> {
    Optional<ChannelEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("update ChannelEntity set attachId = :new where profileId = :prtId and id=:channelId")
    int changeAttach(@Param("new") String attachId,  @Param("prtId")  Integer id, @Param("channelId") String channelId);

    @Transactional
    @Modifying
    @Query("update ChannelEntity set bannerId = :new where profileId = :prtId and id = :channelId")
    int changeBanner(@Param("new") String bannerId, @Param("prtId")  Integer id,  @Param("channelId") String channelId);

    Page<ChannelEntity> findAllByStatus(Status status, Pageable pageable);


    @Transactional
    @Modifying
    @Query("update ChannelEntity set status = 'BLOCK' where id = :id ")
    int changeStatus(@Param("id") String channelId);

    List<ChannelEntity> findAllByStatus(Status status);
}
