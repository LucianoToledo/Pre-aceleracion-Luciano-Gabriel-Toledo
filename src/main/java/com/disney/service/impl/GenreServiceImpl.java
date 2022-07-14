package com.disney.service.impl;

import com.disney.dto.GenreDTO;
import com.disney.mapper.GenreMapper;
import com.disney.repository.GenreRepository;
import com.disney.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GenreServiceImpl implements IGenreService {

    @Autowired
    GenreMapper genreMapper;
    @Autowired
    GenreRepository genreRepository;

    public GenreDTO save(GenreDTO request) {
        //TODO: validate genre name doesn't already exist
        return genreMapper.map(genreRepository.save(genreMapper.map(request)));
    }

    public List<GenreDTO> getAllGenres() {
        return genreMapper.map(genreRepository.findAll());
    }

}
