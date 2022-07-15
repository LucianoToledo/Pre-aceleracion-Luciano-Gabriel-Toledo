package com.disney.mapper;

import com.disney.dto.request.CharacterRequest;
import com.disney.dto.response.CharacterResponse;
import com.disney.entity.CharacterEntity;
import com.disney.entity.MovieEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CharacterMapper {

    public CharacterEntity map(CharacterRequest request) { //requestBasic to entityDto
        CharacterEntity entity = new CharacterEntity();
        entity.setName(request.getName());
        entity.setAge(request.getAge());
        entity.setWeight(request.getWeight());
        entity.setHistory(request.getHistory());
        entity.setImage(request.getImage());
        return entity;
    }

    public CharacterResponse map(CharacterEntity entity, List<MovieEntity> movies) { //entity to responseDto
        CharacterResponse response = new CharacterResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setAge(entity.getAge());
        response.setWeight(entity.getWeight());
        response.setHistory(entity.getHistory());
        response.setImage(entity.getImage());
        response.setMovies(movies);
        return response;
    }

    public CharacterResponse map(CharacterEntity entity) { //entity to responseDto
        CharacterResponse response = new CharacterResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setAge(entity.getAge());
        response.setWeight(entity.getWeight());
        response.setHistory(entity.getHistory());
        response.setImage(entity.getImage());
        return response;
    }

    public List<CharacterResponse> map(List<CharacterEntity> entities) {
        List<CharacterResponse> responseList = new ArrayList<>();
        for (CharacterEntity entity : entities) {
            responseList.add(map(entity));
        }
        return responseList;
    }

}
