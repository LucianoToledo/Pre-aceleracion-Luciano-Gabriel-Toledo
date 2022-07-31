package com.disney.mapper;

import com.disney.dto.request.MovieRequest;
import com.disney.dto.response.*;
import com.disney.entity.CharacterEntity;
import com.disney.entity.MovieEntity;
import com.disney.service.impl.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MovieMapper {
    @Autowired
    CharacterMapper characterMapper;

    @Autowired
    GenreMapper genreMapper;

    @Autowired
    GenreServiceImpl genreService;

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
        // response.setGenreResponse(genreMapper.map(entity.getGenre()));
        return response;
    }

    public MovieResponse map(MovieEntity entity, GenreResponse genreResponse) {
        MovieResponse response = new MovieResponse();
        response.setId(entity.getId());
        response.setTittle(entity.getTitle());
        response.setRanking(entity.getRanking());
        response.setImage(entity.getImage());
        response.setGenreResponse(genreResponse);
        return response;
    }

    public List<MovieResponse> map(List<MovieEntity> entities) {
        List<MovieResponse> responseList = new ArrayList<>();
        for (MovieEntity entity : entities) {
            responseList.add(map(entity));
        }
        return responseList;
    }

    public List<MovieResponse> map(Collection<MovieEntity> entities, boolean loadCharacter) throws Exception {
        List<MovieResponse> responses = new ArrayList<>();
        for (MovieEntity entity : entities) {
            responses.add(map(entity, loadCharacter, false));
        }
        return responses;
    }

    //
    public MovieResponse map(MovieEntity entity, boolean loadCharacter, boolean loadGenre) throws Exception {
        MovieResponse response = map(entity);
        if (loadCharacter) {
            List<CharacterEntity> characterEntities = new ArrayList<>(entity.getCharacters());
            List<CharacterResponse> characterResponses = characterMapper.map(characterEntities, false);
            response.setCharacterResponseList(characterResponses);
        }
        if (loadGenre) {
            GenreResponse genreResponse = genreMapper.map(genreService.getById(entity.getGenreId()));
            response.setGenreResponse(genreResponse);
        }
        return response;
    }

    public MovieBasicResponse mapBasic(MovieEntity entity,boolean loadCharacter, boolean loadGenre) throws Exception {
        MovieBasicResponse response = new MovieBasicResponse();
        response.setTittle(entity.getTitle());
        response.setImage(entity.getImage());
        response.setCreationDate(entity.getCreationDate().toString());
        if (loadCharacter && entity.getCharacters() != null) {
            List<CharacterEntity> characterEntities = new ArrayList<>(entity.getCharacters());
            List<CharacterBasicResponse> characterResponses = characterMapper.mapBasic(characterEntities);
            response.setCharacterResponsesList(characterResponses);
        }
        if (loadGenre && entity.getGenreId() != null) {
            GenreResponse genreResponse = genreMapper.map(genreService.getById(entity.getGenreId()));
            response.setGenreResponse(genreResponse);
        }
        return response;
    }

    public List<MovieBasicResponse> mapBasic(List<MovieEntity> entityList,boolean loadCharacter, boolean loadGenre) throws Exception {
        List<MovieBasicResponse> responseList = new ArrayList<>();
        for (MovieEntity entity : entityList) {
            responseList.add(mapBasic(entity, loadCharacter, loadGenre));
        }
        return responseList;
    }

    public MovieBasicResponse mapResponse2basic(MovieResponse response) {
        MovieBasicResponse basicResponse = new MovieBasicResponse();
        basicResponse.setTittle(response.getTittle());
        basicResponse.setImage(response.getImage());
        basicResponse.setCreationDate(response.getCreationDate());
        return basicResponse;
    }

    public List<MovieBasicResponse> mapResponse2basic(List<MovieResponse> responseList) {
        List<MovieBasicResponse> basicResponseList = new ArrayList<>();
        for (MovieResponse response : responseList) {
            basicResponseList.add(mapResponse2basic(response));
        }
        return basicResponseList;
    }
}
