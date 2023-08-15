package com.example.service;

import com.example.dto.PlaylistDTO;
import com.example.entity.ChannelEntity;
import com.example.entity.PlaylistEntity;
import com.example.enums.PlaylistStatus;
import com.example.exp.AppBadRequestException;
import com.example.mapper.PlayListInfoMapper;
import com.example.mapper.PlayListMapper;
import com.example.mapper.PlayListShortInfoMapper;
import com.example.repository.ChannelRepository;
import com.example.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository  playlistRepository;
    @Autowired
    private ChannelRepository channelRepository;

    public PlaylistDTO create(PlaylistDTO dto, Integer prtId) {
        check(dto);
        Optional<ChannelEntity> optional = channelRepository.findById(dto.getChannel_id());
        if (optional.isEmpty()) {
            throw new AppBadRequestException("there  is no any  channel");
        }
        PlaylistEntity entity = new PlaylistEntity();
        return saveAndReturn(dto, prtId, entity);
    }

    public PlaylistDTO update(Integer playList_id, PlaylistDTO dto, Integer prtId) {
        check(dto);
        PlaylistEntity entity = get(playList_id);
        return saveAndReturn(dto, prtId, entity);
    }

    private PlaylistDTO saveAndReturn(PlaylistDTO dto, Integer prtId, PlaylistEntity entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrtId(prtId);
        entity.setStatus(dto.getStatus());
        entity.setChannelId(dto.getChannel_id());
        entity.setOrderNumber(dto.getOrderNumber());
        playlistRepository.save(entity); // save

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public Boolean changeStatus(Integer play_id) {
        return playlistRepository.changeStatus(PlaylistStatus.PRIVATE.name(), play_id)==1;
    }

    public Boolean delete(Integer play_id) {
        return playlistRepository.delete(play_id)==1;
    }

    public PageImpl<PlayListInfoMapper> pagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "orderNumber");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PlayListInfoMapper> pageObj = playlistRepository.findAll(pageable);
        return new PageImpl<>(pageObj.getContent().stream().toList(), pageable, pageObj.getTotalElements());
    }

    public List<PlayListInfoMapper> list(Integer id) {
        return playlistRepository.findAllByProfileId(id);
    }

    public List<PlayListShortInfoMapper> userPlayList(Integer id) {
        return playlistRepository.userPlayList(id);
    }

    public List<PlayListShortInfoMapper> listByChannel(String channelId) {
        return playlistRepository.listByChannel(PlaylistStatus.PUBLIC.name(), channelId);
    }


    public List<PlayListMapper> listById(Integer id) {
        return playlistRepository.listById(id);
    }


    public PlaylistEntity get(Integer id) {
        return playlistRepository.findById(id).orElseThrow(() -> new AppBadRequestException("playlist not found"));
    }


    private void check(PlaylistDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new AppBadRequestException("Name qani?");
        }
        if (dto.getChannel_id()==null|| dto.getChannel_id().isBlank()){
           throw new AppBadRequestException("channelId qani?");
        }
        if (dto.getOrderNumber()==null){
            throw new AppBadRequestException("order number  qani?");
        }
    }


}
