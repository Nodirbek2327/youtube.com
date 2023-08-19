package com.example.service;

import com.example.dto.ChannelDTO;
import com.example.entity.AttachEntity;
import com.example.entity.ChannelEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.Status;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.AttachRepository;
import com.example.repository.ChannelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private AttachRepository attachRepository;

    public ChannelDTO create(ChannelDTO dto, Integer prtId) {
        check(dto);
        Optional<ChannelEntity> channelEntity = channelRepository.findByName(dto.getName());
        if (channelEntity.isPresent()) {
            log.warn("channel_name: {}  already exists"+dto.getName());
            throw new AppBadRequestException("name already exists");
        }
        ChannelEntity entity = new ChannelEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setAttachId(dto.getAttachId());
        entity.setBannerId(dto.getBannerId());
        entity.setProfileId(prtId);
        entity.setStatus(Status.ACTIVE);

        channelRepository.save(entity);
        log.info("channel created");

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ChannelDTO update(String channel_id, ChannelDTO dto, Integer prtId) {
        check(dto);
        ChannelEntity entity = get(channel_id);
        entity.setProfileId(prtId);
        entity.setStatus(Status.ACTIVE);
        entity.setBannerId(dto.getBannerId());
        entity.setAttachId(dto.getAttachId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        channelRepository.save(entity); // save
        log.info("channel updated");
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean updateAttach(ProfileEntity profileEntity, String attachId, String channelId) {
        if (profileEntity.getImageId() != null){
            attachRepository.deleteById(profileEntity.getImageId());
        }
         Optional<AttachEntity> optional = attachRepository.findById(attachId);
        if (optional.isPresent()){
            return channelRepository.changeAttach(attachId, profileEntity.getId(), channelId )==1;
        }
        return false;
    }

    public Boolean updateBanner(ProfileEntity profileEntity, String attachId, String id) {
        if (profileEntity.getImageId() != null){
            attachRepository.deleteById(profileEntity.getImageId());
        }
        Optional<AttachEntity> optional = attachRepository.findById(attachId);
        if (optional.isPresent()){
            return channelRepository.changeBanner(attachId, profileEntity.getId(), id)==1;
        }
        return false;
    }


    public PageImpl<ChannelDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChannelEntity> pageObj = channelRepository.findAllByStatus(Status.ACTIVE, pageable);
        return new PageImpl<>(getDTOS(pageObj.getContent().stream().toList()), pageable, pageObj.getTotalElements());
    }

    public ChannelDTO getChannel(String channelId) {
        ChannelEntity entity = channelRepository.findById(channelId).orElseThrow(() -> new AppBadRequestException("channel not found"));
        return toDTO(entity);
    }

    public Boolean changeStatus(String channelId) {
       return channelRepository.changeStatus(channelId)==1;
    }

    public List<ChannelDTO> list() {
        return getDTOS( channelRepository.findAllByStatus(Status.ACTIVE));
    }


    public ChannelEntity get(String channelId) {
        return channelRepository.findById(channelId).orElseThrow(() -> new AppBadRequestException("channel not found"));
    }


    private void check(ChannelDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            log.warn("channel name qani to create");
            throw new AppBadRequestException("Name qani?");
        }
    }

    public ChannelDTO toDTO(ChannelEntity entity){
        ChannelDTO dto = new ChannelDTO();
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setBannerId(entity.getBannerId());
        dto.setDescription(entity.getDescription());
        dto.setProfileId(entity.getProfileId());
        dto.setAttachId(entity.getAttachId());
        return dto;
    }

    private List<ChannelDTO> getDTOS(List<ChannelEntity> list) {
        if (list.isEmpty()) {
            log.warn("channel not found");
            throw  new ItemNotFoundException("channel not found");
        }
        List<ChannelDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            dtoList.add(toDTO(entity));
        });
        return dtoList;
    }



}
