package com.disney.service.impl;

import com.disney.dto.request.MovieFiltersRequest;
import com.disney.dto.request.MovieRequest;
import com.disney.dto.response.MovieResponse;
import com.disney.entity.CharacterEntity;
import com.disney.entity.MovieEntity;
import com.disney.mapper.MovieMapper;
import com.disney.repository.MovieRepository;
import com.disney.repository.specifications.MovieSpecification;
import com.disney.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImpl implements IMovieService {

    @Autowired
    MovieMapper movieMapper;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieSpecification movieSpecification;

    @Autowired
    CharacterServiceImpl characterService;

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
        entity.setTitle(request.getTitle());
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
        return movieMapper.map(movieRepository.findAll(), true);
    }

    @Override
    public List<MovieResponse> getByFilters(String name, String date, Set<String> characters, String order) {
        MovieFiltersRequest movieFiltersRequest = new MovieFiltersRequest(name, date, characters, order);
        List<MovieEntity> entities = movieRepository.findAll(movieSpecification.getByFilters(movieFiltersRequest));
        return movieMapper.map(entities, true);

    }

    @Override
    @Transactional
    public MovieResponse addCharacter(String idMovie, String idCharacter) throws Exception {
        MovieEntity movie = getByIdAndSoftDeleteFalse(idMovie);
        Set<CharacterEntity> characterEntitySet = movie.getCharacters();
        CharacterEntity character = characterService.getByIdAndSoftDeleteFalse(idCharacter);
        if (characterEntitySet.contains(character)) {
            throw new Exception("The character already belongs to the movie");
        }
        characterEntitySet.add(character);
        return movieMapper.map(movieRepository.save(movie), true);
    }

    @Override
    @Transactional
    public MovieResponse removeCharacter(String idMovie, String idCharacter) throws Exception {
        MovieEntity movie = getByIdAndSoftDeleteFalse(idMovie);
        Set<CharacterEntity> characterEntitySet = movie.getCharacters();
        CharacterEntity character = characterService.getByIdAndSoftDeleteFalse(idCharacter);
        if (!characterEntitySet.contains(character)) {
            throw new Exception("The character does not belong to the movie");
        }
        characterEntitySet.remove(character);
        return movieMapper.map(movieRepository.save(movie), true);
    }

    private void validateRequest(MovieRequest request) throws Exception {
        if (movieRepository.findByTitle(request.getTitle()) != null) {
            throw new Exception("Tittle is already in use");
        }
    }
}
//    @Override  //este funca pero lo pide por pathvariable, no por json
//    @Transactional
//    public MovieResponse addCharacters(AddCharacter2Movie request) throws EntityNotFoundException {
//        MovieEntity movie = getByIdAndSoftDeleteFalse(request.getIdMovie());
//        Set<CharacterEntity> characterEntitySet = movie.getCharacters();
//        for (String aux : request.getIdCharacteres()) {
//            characterEntitySet.add(characterService.getByIdAndSoftDeleteFalse(aux));
//        }
//        return movieMapper.map(movieRepository.save(movie),true);
//    }