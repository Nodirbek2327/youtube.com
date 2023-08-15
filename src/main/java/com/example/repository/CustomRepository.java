package com.example.repository;

import com.example.dto.EmailHistoryFilterDTO;
import com.example.dto.FilterDTO;
import com.example.entity.EmailHistoryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomRepository {
     @Autowired
     private EntityManager entityManager;
    public FilterDTO<EmailHistoryEntity> filterEmail(EmailHistoryFilterDTO filterDTO, int page, int size) {

        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filterDTO.getEmail() != null) {
            stringBuilder.append(" and s.email =:email");
            params.put("email", filterDTO.getEmail());
        }
        if (filterDTO.getCreated_date_from() != null && filterDTO.getCreated_date_to() != null) {
            stringBuilder.append(" and s.createdDate between :dateFrom and :dateTo ");
            params.put("dateFrom", LocalDateTime.of(filterDTO.getCreated_date_from(), LocalTime.MIN));
            params.put("dateTo", LocalDateTime.of(filterDTO.getCreated_date_to(), LocalTime.MAX));
        } else if (filterDTO.getCreated_date_from() != null) {
            stringBuilder.append(" and s.createdDate >= :dateFrom");
            params.put("dateFrom", LocalDateTime.of(filterDTO.getCreated_date_from(), LocalTime.MIN));
        } else if (filterDTO.getCreated_date_to() != null) {
            stringBuilder.append(" and s.createdDate <= :dateTo");
            params.put("dateFrom", LocalDateTime.of(filterDTO.getCreated_date_to(), LocalTime.MAX));
        }

        String selectBuilder = "select s from EmailHistoryEntity as s where s.visible = true " + stringBuilder +
                " order by createdDate desc";

        String countBuilder = "select count(s) from EmailHistoryEntity as s where s.visible=true" + stringBuilder;

        Query selectQuery = entityManager.createQuery(selectBuilder);
        selectQuery.setMaxResults(size); // limit
        selectQuery.setFirstResult(size * page); // offset

        Query countQuery = entityManager.createQuery(countBuilder);
        // params
        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<EmailHistoryEntity> entityList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return  new FilterDTO<>(entityList, totalCount);
    }

}
