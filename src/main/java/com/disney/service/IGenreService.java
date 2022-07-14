package com.disney.service;

import com.disney.dto.GenreDTO;
import java.util.List;

public interface IGenreService {
    GenreDTO save(GenreDTO request);

    List<GenreDTO> getAllGenres();
}
