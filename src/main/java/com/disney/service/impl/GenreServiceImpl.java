package com.disney.service.impl;

import com.disney.dto.request.GenreRequest;
import com.disney.dto.response.GenreResponse;
import com.disney.entity.GenreEntity;
import com.disney.mapper.GenreMapper;
import com.disney.repository.GenreRepository;
import com.disney.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements IGenreService {

    @Autowired
    GenreMapper genreMapper;
    @Autowired
    GenreRepository genreRepository;

    @Override
    public GenreResponse save(GenreRequest request) {
        return genreMapper.map(genreRepository.save(genreMapper.map(request)));
    }

    @Override
    public GenreResponse update(GenreRequest request) throws Exception {
        GenreEntity entity = getByIdAndSoftDeleteFalse(request.getId());
        entity.setName(request.getName().toUpperCase());
        entity.setImage(request.getImage());
        return genreMapper.map(genreRepository.save(entity));
    }

    @Override
    public GenreResponse enableGenre(String id) throws Exception {
        GenreEntity entity = getById(id);
        if (entity.isEnabled()) {
            throw new Exception("Genre is already enable");
        }
        entity.setSoftDelete(false);
        return genreMapper.map(genreRepository.save(entity));
    }

    @Override
    public GenreResponse disableGenre(String id) throws Exception {
        GenreEntity entity = getById(id);
        if (!entity.isEnabled()) {
            throw new Exception("Genre is already disable");
        }
        entity.setSoftDelete(true);
        return genreMapper.map(genreRepository.save(entity));
    }

    @Override
    public GenreEntity getByIdAndSoftDeleteFalse(String id) throws Exception {
        Optional<GenreEntity> opt = Optional.ofNullable(genreRepository.findByIdAndSoftDeleteFalse(id));
        if (opt.isEmpty()) {
            throw new Exception("Genre not found or disable");
        }
        return opt.get();
    }

    @Override
    public GenreEntity getById(String id) throws Exception {
        Optional<GenreEntity> opt = genreRepository.findById(id);
        if (opt.isEmpty()) {
            throw new Exception("Genre not found");
        }
        return opt.get();
    }

    public List<GenreResponse> getAllGenres() {
        return genreMapper.map(genreRepository.findAll());
    }

    @Override
    public GenreEntity getByNameAndSoftDeleteFalse(String name) {
        Optional<GenreEntity> opt = Optional.ofNullable(genreRepository.findByNameAndSoftDeleteFalse(name));
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Genre not found or disable");
        }
        return opt.get();
    }

}
