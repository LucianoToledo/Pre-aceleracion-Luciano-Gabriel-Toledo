package com.disney.mapper;

import com.disney.dto.request.MovieRequest;
import com.disney.dto.response.CharacterResponse;
import com.disney.dto.response.MovieResponse;
import com.disney.entity.CharacterEntity;
import com.disney.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MovieMapper {
    @Autowired
    CharacterMapper characterMapper;

    public MovieEntity map(MovieRequest request) {
        MovieEntity entity = new MovieEntity();
        entity.setTitle(request.getTitle());
        entity.setRanking(request.getRanking());
        entity.setImage(request.getImage());
        return entity;
    }

    public MovieResponse map(MovieEntity entity) {
        MovieResponse response = new MovieResponse();
        response.setId(entity.getId());
        response.setTittle(entity.getTitle());
        response.setRanking(entity.getRanking());
        response.setImage(entity.getImage());
        return response;
    }

    public MovieResponse map(MovieEntity entity, List<CharacterResponse> characterEntityList) {
        MovieResponse response = new MovieResponse();
        response.setId(entity.getId());
        response.setTittle(entity.getTitle());
        response.setRanking(entity.getRanking());
        response.setImage(response.getImage());
        response.setCharacterResponseList(characterEntityList);
        return response;
    }

    public List<MovieResponse> map(List<MovieEntity> entities) {
        List<MovieResponse> responseList = new ArrayList<>();
        for (MovieEntity entity : entities) {
            responseList.add(map(entity));
        }
        return responseList;
    }

    public List<MovieResponse> map(Collection<MovieEntity> entities, boolean loadCharacter) {
        List<MovieResponse> responses = new ArrayList<>();
        for (MovieEntity entity : entities) {
            responses.add(this.map(entity, loadCharacter));
        }
        return responses;
    }

    //
    public MovieResponse map(MovieEntity entity, boolean loadCharacter) {
        MovieResponse response = map(entity);
        if (loadCharacter) {
            List<CharacterEntity> characterEntities = new ArrayList<>(entity.getCharacters());
            List<CharacterResponse> characterResponses = characterMapper.map(characterEntities, false);
            response.setCharacterResponseList(characterResponses);
        }
        return response;
    }
}
