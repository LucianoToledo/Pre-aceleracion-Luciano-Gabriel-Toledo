package com.disney.service.impl;

import com.disney.dto.request.MovieRequest;
import com.disney.dto.response.MovieResponse;
import com.disney.entity.MovieEntity;
import com.disney.mapper.MovieMapper;
import com.disney.repository.MovieRepository;
import com.disney.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements IMovieService {

    @Autowired
    MovieMapper movieMapper;

    @Autowired
    MovieRepository movieRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public MovieResponse save(MovieRequest request) throws Exception {
        validateRequest(request);
        return movieMapper.map(movieRepository.save(movieMapper.map(request)));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public MovieResponse update(MovieRequest request) {
        MovieEntity entity = getByIdAndSoftDeleteFalse(request.getId());
        entity.setTitle(request.getTittle());
        entity.setRanking(request.getRanking());
        entity.setImage(request.getImage());
        return movieMapper.map(movieRepository.save(entity));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void enableMovie(String id) throws Exception {
        MovieEntity entity = getById(id);
        if (entity.isEnabled()) {
            throw new Exception("Movie is already enable");
        }
        entity.setSoftDelete(false);
        movieRepository.save(entity);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void disableMovie(String id) throws Exception {
        MovieEntity entity = getById(id);
        if (!entity.isEnabled()) {
            throw new Exception("Movie is already disable");
        }
        entity.setSoftDelete(true);
        movieRepository.save(entity);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public MovieEntity getByIdAndSoftDeleteFalse(String id) throws EntityNotFoundException {
        Optional<MovieEntity> opt = Optional.ofNullable(movieRepository.findByIdAndSoftDeleteFalse(id));
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Movie not found or disable");
        }
        return opt.get();
    }

    @Override
    @Transactional
    public MovieEntity getById(String id) throws EntityNotFoundException {
        Optional<MovieEntity> opt = movieRepository.findById(id);
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Movie not found or disable");
        }
        return opt.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> getAll() {
        return movieMapper.map(movieRepository.findAll());
    }

    private void validateRequest(MovieRequest request) throws Exception {
        if (movieRepository.findByTitle(request.getTittle()) != null) {
            throw new Exception("Tittle is already in use");
        }
    }
}
