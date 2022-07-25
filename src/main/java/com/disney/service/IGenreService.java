package com.disney.service;

import com.disney.dto.request.GenreRequest;
import com.disney.dto.request.GenreRequest;
import com.disney.dto.response.GenreResponse;
import com.disney.dto.response.GenreResponse;
import com.disney.entity.GenreEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface IGenreService {
    GenreResponse save(GenreRequest request);

    GenreResponse update(GenreRequest request) throws Exception;
    void enableGenre(String id) throws Exception;
    void disableGenre(String id) throws Exception;
    GenreEntity getByIdAndSoftDeleteFalse(String id) throws Exception;

    GenreEntity getById(String id) throws Exception;
    List<GenreResponse> getAllGenres();

    GenreEntity getByNameAndSoftDeleteFalse(String genreName);
}
