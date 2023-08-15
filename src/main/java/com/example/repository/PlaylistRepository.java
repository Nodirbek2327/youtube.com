package com.example.repository;

import com.example.entity.PlaylistEntity;
import com.example.mapper.PlayListInfoMapper;
import com.example.mapper.PlayListMapper;
import com.example.mapper.PlayListShortInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Integer> {
    @Query("select a.id, a.name, a.description, a.status, a.orderNumber, a.createdDate, a.channel, p from PlaylistEntity as a inner" +
            " join ProfileEntity as p on  p.id = a.prtId  where  a.visible = true ")
    Page<PlayListInfoMapper> findAll(Pageable pageable);

    @Query("update PlaylistEntity  set status = :new where id = :id ")
    int changeStatus(@Param("new") String status,   @Param("id") Integer playId);

    @Query("update PlaylistEntity  set visible = false where id = :id ")
    int delete(@Param("id") Integer playId);


    @Query("select a.id, a.name, a.description, a.status, a.orderNumber, a.createdDate, a.channel, p from PlaylistEntity as a inner" +
            " join ProfileEntity as p on  p.id = a.prtId  where a.visible = true and a.prtId=:prtId order by a.orderNumber desc ")
    List<PlayListInfoMapper> findAllByProfileId(@Param("prtId") Integer prtId);


    @Query("SELECT a.id, a.name, a.createdDate, " +
            " a.channel, COUNT(v), " +
            "COLLECT(VideoEntity) " +
            "FROM PlaylistEntity a " +
            "JOIN VideoEntity v ON v.channelId = a.channel.id " +
            "WHERE a.visible = true AND a.prtId = :prtId " +
            "GROUP BY a.id, a.name, a.createdDate, a.channel")
    List<PlayListShortInfoMapper> userPlayList(@Param("prtId") Integer prtId);

    @Query("SELECT a.id, a.name, a.createdDate, " +
            " a.channel, COUNT(v) as videoCount, " +
            "COLLECT(VideoEntity)  as videoList " +
            "FROM PlaylistEntity a " +
            "JOIN VideoEntity v ON v.channelId = a.channel.id " +
            "WHERE a.visible = true AND a.channelId = :channelId and a.status = :status " +
            "GROUP BY a.id, a.name, a.createdDate, a.channel")
    List<PlayListShortInfoMapper> listByChannel(@Param("status") String status,  @Param("channelId") String channelId);

    @Query("SELECT a.id, a.name,  " +
            " COUNT(v) as videoCount, sum(v.viewCount) as total_view_count, v.publishedDate as  last_update_date " +
            "FROM PlaylistEntity a " +
            "JOIN VideoEntity v ON v.channelId = a.channel.id " +
            "WHERE a.visible = true AND a.id = :id " +
            "GROUP BY a.id, a.name, a.createdDate, a.channel")
    List<PlayListMapper> listById(@Param("id") Integer id);


}
