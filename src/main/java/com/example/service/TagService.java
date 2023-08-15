package com.example.service;

import com.example.dto.TagDTO;
import com.example.entity.TagEntity;
import com.example.exp.AppBadRequestException;
import com.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagDTO create(TagDTO dto, Integer prtId) {
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        entity.setPrtId(prtId);
        tagRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(Integer id, TagDTO dto, Integer prtId) {
        TagEntity entity = get(id); // get
        entity.setName(dto.getName());
        entity.setId(id);
        entity.setPrtId(prtId);
        tagRepository.save(entity);
        return true;
    }

    public TagEntity get(Integer id) {
        return tagRepository.findById(id).orElseThrow(() -> new AppBadRequestException("tag not found"));
    }

    public Boolean delete(Integer id) {
        return tagRepository.delete(id)==1;
    }

    public List<TagDTO> List() {
        List<TagDTO> list = new LinkedList<>();
        tagRepository.findAll().forEach(entity->{
            list.add(toDTO(entity));
        });
        return list;
    }
    public TagDTO toDTO(TagEntity entity){
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
