package com.disney.service;

import com.disney.dto.request.MovieRequest;
import com.disney.dto.response.MovieResponse;
import com.disney.entity.MovieEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface IMovieService {
    
    MovieResponse save(MovieRequest request) throws Exception;
    MovieResponse update(MovieRequest request);
    void enableMovie(String id) throws Exception;
    void disableMovie(String id) throws Exception;
    MovieEntity getByIdAndSoftDeleteFalse(String id) throws EntityNotFoundException;

    MovieEntity getById(String id) throws EntityNotFoundException;
    List<MovieResponse> getAll();
}
