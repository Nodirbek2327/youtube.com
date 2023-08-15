package com.example.repository;

import com.example.entity.ProfileEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer>, PagingAndSortingRepository<ProfileEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update ProfileEntity  set name =?2, surname =?3 where email =?1")
    int updateDetail(String email, String name, String surname);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set password = :new where id =:id")
    int changePassword(@Param("new") String newPassword, @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible = false where id =:id")
    int delete(@Param("id") Integer id);

    Optional<ProfileEntity> findByEmail(String email);


    @Query("update ProfileEntity set imageId = :new where id =:id")
    int changeAttach(@Param("new") String newAttach, @Param("id") Integer id);

}
