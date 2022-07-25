package com.disney.service;

import com.disney.dto.request.MovieRequest;
import com.disney.dto.response.CharacterBasicResponse;
import com.disney.dto.response.MovieBasicResponse;
import com.disney.dto.response.MovieResponse;
import com.disney.entity.MovieEntity;
import com.disney.exception.EntityNotFound;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

public interface IMovieService {

    MovieResponse save(MovieRequest request) throws Exception;

    MovieResponse update(MovieRequest request) throws Exception;

    void enableMovie(String id) throws Exception;

    void disableMovie(String id) throws Exception;

    MovieEntity getByIdAndSoftDeleteFalse(String id) throws EntityNotFoundException;

    MovieEntity getById(String id) throws EntityNotFoundException;

    List<MovieResponse> getAll() throws Exception;


    MovieResponse addCharacter(String idMovie, String idCharacter) throws Exception;

    MovieResponse removeCharacter(String idMovie, String idCharacter) throws Exception;


    MovieResponse addGenre(String idMovie, String idGenre) throws Exception;


    List<MovieBasicResponse> getByQuery(Map<String, String> modelMap) throws Exception;

}
    //List<MovieResponse> getByFilters(String name, String date, List<String> characters, String order) throws Exception;
//    List<MovieBasicResponse> getByTitle(String title, String order) throws EntityNotFound;
//    List<MovieBasicResponse> getByGenre(String genre, String order) throws EntityNotFound;
