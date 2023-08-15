package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.entity.AttachEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.AttachRepository;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AttachRepository attachRepository;

    public ProfileDTO create(ProfileDTO dto, Integer prtId) {
        check(dto);
        Optional<ProfileEntity> profileByEmail = profileRepository.findByEmail(dto.getEmail());
        if (profileByEmail.isPresent()) {
            throw new AppBadRequestException("Email already exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(dto.getRole());
        entity.setPrtId(prtId);
        profileRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean changePassword(String newPassword, Integer id) {
        int effectedRows = profileRepository.changePassword(newPassword, id);
        return effectedRows == 1;
    }

    public Boolean updateDetail(String email, ProfileDTO dto) {
        int effectedRows = profileRepository.updateDetail(email, dto.getName(), dto.getSurname());
        return effectedRows == 1;
    }

    public Boolean updateAttach(ProfileEntity profile, String attachId) {
        if (profile.getImageId() != null){
            attachRepository.deleteById(profile.getImageId());
        }
        return profileRepository.changeAttach(attachId, profile.getId())==1;
    }

    public ProfileDTO getProfile(Integer profileId) {
        ProfileEntity entity = profileRepository.findById(profileId).orElseThrow(() -> new AppBadRequestException("Profile not found"));
        ProfileDTO dto = new ProfileDTO();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        AttachEntity attachEntity =  entity.getImage();
        dto.setImageUrl(attachEntity.getUrl());
        return dto;
    }



    public ProfileEntity get(Integer profileId) {
        return profileRepository.findById(profileId).orElseThrow(() -> new AppBadRequestException("Profile not found"));
    }


    public ProfileDTO add(ProfileDTO dto) {
        dto.setStatus(ProfileStatus.ACTIVE);
        check(dto);
        ProfileEntity entity = toEntity(dto);
        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean delete(Integer id) {
        return profileRepository.delete(id)==1;
    }


    private void check(ProfileDTO profileDTO) {
        if (profileDTO.getName() == null || profileDTO.getName().isBlank()) {
            throw new AppBadRequestException("Name qani?");
        }
        if (profileDTO.getSurname() == null || profileDTO.getSurname().isBlank()) {
            throw new AppBadRequestException("Surname qani?");
        }
        if (profileDTO.getPassword() == null || profileDTO.getPassword().isBlank()) {
            throw new AppBadRequestException("password qani?");
        }
    }

    public ProfileDTO toDTO(ProfileEntity entity){
        ProfileDTO dto = new ProfileDTO();
       // dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
    //    dto.setPhoto_id(entity.getPhoto_id());
        dto.setVisible(entity.getVisible());
        dto.setPassword(entity.getPassword());
        return dto;
    }

    public ProfileEntity toEntity(ProfileDTO dto){
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(dto.getRole());
        entity.setEmail(dto.getEmail());
        entity.setStatus(dto.getStatus());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        return entity;
    }

    private List<ProfileDTO> getProfileDTOS(List<ProfileEntity> list) {
        if (list.isEmpty()) {
            throw  new ItemNotFoundException("profile not found");
        }
        List<ProfileDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }



}
