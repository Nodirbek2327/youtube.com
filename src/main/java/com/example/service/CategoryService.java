package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.exp.AppBadRequestException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto, Integer prtId) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        entity.setPrtId(prtId);
        categoryRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(Integer id, CategoryDTO dto, Integer prtId) {
        CategoryEntity entity = get(id); // get
        entity.setName(dto.getName());
        entity.setId(id);
        entity.setPrtId(prtId);
        categoryRepository.save(entity);
        return true;
    }

    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new AppBadRequestException("category not found"));
    }

    public Boolean delete(Integer id) {
        return categoryRepository.delete(id)==1;
    }

    public List<CategoryDTO> List() {
        List<CategoryDTO> list = new LinkedList<>();
        categoryRepository.findAll().forEach(entity->{
            list.add(toDTO(entity));
        });
        return list;
    }
    public CategoryDTO toDTO(CategoryEntity entity){
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
