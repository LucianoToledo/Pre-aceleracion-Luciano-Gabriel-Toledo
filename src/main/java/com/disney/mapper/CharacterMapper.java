package com.disney.mapper;

import com.disney.dto.request.CharacterRequest;
import com.disney.dto.response.CharacterResponse;
import com.disney.dto.response.MovieResponse;
import com.disney.entity.CharacterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class CharacterMapper {

    @Autowired
    MovieMapper movieMapper;

    public CharacterEntity map(CharacterRequest request) { //requestBasic to entityDto
        CharacterEntity entity = new CharacterEntity();
        entity.setName(request.getName());
        entity.setAge(request.getAge());
        entity.setWeight(request.getWeight());
        entity.setHistory(request.getHistory());
        entity.setImage(request.getImage());
        return entity;
    }

    public CharacterResponse map(CharacterEntity entity, List<MovieResponse> movies) {
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

    public List<CharacterResponse> map(List<CharacterEntity> entities, boolean loadMovies){
        List<CharacterResponse> responseList = new ArrayList<>();
        for (CharacterEntity entity : entities) {
            responseList.add(map(entity, loadMovies));
        }
        return responseList;
    }

    public CharacterResponse map(CharacterEntity entity, boolean loadMovies){
        CharacterResponse response = map(entity);
        if (loadMovies){
            List<MovieResponse> movieResponses = this.movieMapper.map(entity.getMovies(),false);
            response.setMovies(movieResponses);
        }
        return response;
    }

}
