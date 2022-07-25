package com.disney.mapper;

import com.disney.dto.request.GenreRequest;
import com.disney.dto.response.GenreResponse;
import com.disney.entity.GenreEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenreMapper {

    public GenreEntity map(GenreRequest request){ //request to entityDto
        GenreEntity entity = new GenreEntity();
        entity.setName(request.getName().toUpperCase());
        entity.setImage(request.getImage());
        return entity;
    }

    public GenreResponse map(GenreEntity entity){ //entity to responseDto
        GenreResponse response = new GenreResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setImage(entity.getImage());
        return response;
    }

    public List<GenreResponse> map(List<GenreEntity> entities){
        List<GenreResponse> responseList = new ArrayList<>();
        for (GenreEntity entity:entities) {
            responseList.add(map(entity));
        }
        return responseList;
    }
}
