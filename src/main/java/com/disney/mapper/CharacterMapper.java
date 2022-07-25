package com.disney.mapper;

import com.disney.dto.request.CharacterRequest;
import com.disney.dto.response.CharacterBasicResponse;
import com.disney.dto.response.CharacterResponse;
import com.disney.dto.response.MovieResponse;
import com.disney.entity.CharacterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class CharacterMapper {

    @Lazy
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

    public CharacterResponse map(CharacterEntity entity) {
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

    public List<CharacterResponse> map(List<CharacterEntity> entities, boolean loadMovies) throws Exception {
        List<CharacterResponse> responseList = new ArrayList<>();
        for (CharacterEntity entity : entities) {
            responseList.add(map(entity, loadMovies));
        }
        return responseList;
    }

    public CharacterResponse map(CharacterEntity entity, boolean loadMovies) throws Exception {
        CharacterResponse response = map(entity);
        if (loadMovies){
            List<MovieResponse> movieResponses = movieMapper.map(entity.getMovies(),false);
            response.setMovies(movieResponses);
        }
        return response;
    }

    public CharacterBasicResponse mapBasic(CharacterEntity entity){
        CharacterBasicResponse response = new CharacterBasicResponse();
        response.setName(entity.getName());
        response.setImage(entity.getImage());
        response.setAge(entity.getAge().toString());
        return response;
    }

    public List<CharacterBasicResponse> mapBasic(List<CharacterEntity> entityList){
        List<CharacterBasicResponse> responseList = new ArrayList<>();
        for (CharacterEntity entity:entityList) {
            responseList.add(mapBasic(entity));
        }
        return responseList;
    }

    public CharacterBasicResponse mapResponse2basic(CharacterResponse response){
        CharacterBasicResponse basicResponse = new CharacterBasicResponse();
        basicResponse.setName(response.getName());
        basicResponse.setImage(response.getImage());
        basicResponse.setAge(response.getAge().toString());
        return basicResponse;
    }

    public List<CharacterBasicResponse> mapResponse2basic(List<CharacterResponse> responseList){
        List<CharacterBasicResponse> basicResponses = new ArrayList<>();
        for (CharacterResponse response:responseList) {
            basicResponses.add(mapResponse2basic(response));
        }
        return basicResponses;
    }
}
