package com.disney.mapper;

import com.disney.dto.GenreDTO;
import com.disney.entity.GenreEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenreMapper {

    public GenreEntity map(GenreDTO request){ //request to entityDto
        GenreEntity genre = new GenreEntity();
        genre.setName(request.getName());
        genre.setImage(request.getImage());
        return genre;
    }

    public GenreDTO map(GenreEntity entity){ //entity to responseDto
        GenreDTO response = new GenreDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setImage(entity.getImage());
        return response;
    }

    public List<GenreDTO> map(List<GenreEntity> genres){
        List<GenreDTO> dtos = new ArrayList<>();
        for (GenreEntity entity:genres) {
            dtos.add(map(entity));
        }
        return dtos;
    }
}
