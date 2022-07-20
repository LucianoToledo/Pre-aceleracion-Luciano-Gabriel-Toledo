package com.disney.service;

import com.disney.dto.request.MovieRequest;
import com.disney.dto.response.MovieResponse;
import com.disney.entity.MovieEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

public interface IMovieService {

    MovieResponse save(MovieRequest request) throws Exception;

    MovieResponse update(MovieRequest request);

    void enableMovie(String id) throws Exception;

    void disableMovie(String id) throws Exception;

    MovieEntity getByIdAndSoftDeleteFalse(String id) throws EntityNotFoundException;

    MovieEntity getById(String id) throws EntityNotFoundException;

    List<MovieResponse> getAll();

    List<MovieResponse> getByFilters(String name, String date, List<String> characters, String order);

    MovieResponse addCharacter(String idMovie, String idCharacter) throws Exception;

    MovieResponse removeCharacter(String idMovie, String idCharacter) throws Exception;

    List<MovieResponse> findByTitle(String title) throws EntityNotFoundException;


}
