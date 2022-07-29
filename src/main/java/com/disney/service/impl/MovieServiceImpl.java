package com.disney.service.impl;

import com.disney.dto.request.MovieFiltersRequest;
import com.disney.dto.request.MovieRequest;
import com.disney.dto.response.GenreResponse;
import com.disney.dto.response.MovieBasicResponse;
import com.disney.dto.response.MovieResponse;
import com.disney.entity.CharacterEntity;
import com.disney.entity.MovieEntity;
import com.disney.exception.EntityNotFound;
import com.disney.mapper.GenreMapper;
import com.disney.mapper.MovieMapper;
import com.disney.repository.MovieRepository;
import com.disney.repository.specifications.MovieSpecification;
import com.disney.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

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

    @Autowired
    GenreServiceImpl genreService;

    @Autowired
    GenreMapper genreMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public MovieResponse save(MovieRequest request) throws Exception { //todo: crear un personaje nuevo y asociarlo
        validateRequest(request);
        MovieEntity entity = movieMapper.map(request);
        if (request.getIdCharacters() != null) {
            entity.setCharacters(addCharacters(request.getIdCharacters()));
        }
        return movieMapper.map(movieRepository.save(entity), false, false);
    }

    private Set<CharacterEntity> addCharacters(List<String> charactersId) {
        Set<CharacterEntity> characterEntities = new HashSet<>();
        for (String aux : charactersId) {
            characterEntities.add(characterService.getByIdAndSoftDeleteFalse(aux));
        }
        return characterEntities;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public MovieResponse update(MovieRequest request) throws Exception {
        MovieEntity entity = getByIdAndSoftDeleteFalse(request.getId());
        entity.setTitle(request.getTitle());
        entity.setRanking(request.getRanking());
        entity.setImage(request.getImage());
        return movieMapper.map(movieRepository.save(entity), true, true);
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
    public List<MovieResponse> getAll() throws Exception {
        return movieMapper.map(movieRepository.findAll(), true);
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
        return movieMapper.map(movieRepository.save(movie), true, true);
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
        return movieMapper.map(movieRepository.save(movie), true, false);
    }

    @Override
    @Transactional
    public MovieResponse addGenre(String idMovie, String idGenre) throws Exception {
        MovieEntity movie = getByIdAndSoftDeleteFalse(idMovie);
        GenreResponse genreResponse = genreMapper.map((genreService.getByIdAndSoftDeleteFalse(idGenre)));
        movie.setGenreId(genreResponse.getId());
        return movieMapper.map(movieRepository.save(movie), genreResponse);
    }

    public List<MovieBasicResponse> getByFilters(String title, Set<String> characters, String order) throws Exception {
        MovieFiltersRequest movieFilters = new MovieFiltersRequest(title, characters, order);
        List<MovieEntity> entityList = movieRepository.findAll(movieSpecification.getByFilters(movieFilters));
        return movieMapper.mapBasic(entityList, true, true);
    }

//    @Override
//    @Transactional
//    public List<MovieBasicResponse> getByQuery(Map<String, String> modelMap) throws Exception {
//        if (modelMap.isEmpty()) {
//            return movieMapper.mapResponse2basic(getAll());
//        }
//        List<Optional<MovieEntity>> opt = new ArrayList<>();
//        boolean order = true; //true = ASC
//        for (Map.Entry entry : modelMap.entrySet()) {
//            if (entry.getKey().toString().equalsIgnoreCase("order") &&
//                    entry.getValue().toString().equalsIgnoreCase("DESC")) {
//                order = false;
//            }
//        }
//        for (Map.Entry entry : modelMap.entrySet()) {
//            String key = entry.getKey().toString();
//            if (key.equalsIgnoreCase("title")) {
//                opt = order ? movieRepository.findByTitleAsc(entry.getValue().toString())
//                        : movieRepository.findByTitleDesc(entry.getValue().toString());
//            }
//            if (key.equalsIgnoreCase("genre")) {
//                opt = order ? movieRepository.findByGenreAsc(entry.getValue().toString())
//                        : movieRepository.findByGenreDesc(entry.getValue().toString());
//            }
//        }
//        List<MovieBasicResponse> responseList = movieMapper.mapBasic(getOptional(opt));
//        HashSet<String> withOutDuplicates = new HashSet<>();
//        responseList.removeIf(e -> !withOutDuplicates.add(e.getTittle()));
//        return responseList;
//    }

    private List<MovieEntity> getOptional(List<Optional<MovieEntity>> optList) throws EntityNotFound {
        List<MovieEntity> entityList = new ArrayList<>();
        if (optList.isEmpty()) {
            throw new EntityNotFound("Characters not found or disabled");
        }
        for (Optional<MovieEntity> opt : optList) {
            entityList.add(opt.get());
        }
        return entityList;
    }

    private void validateRequest(MovieRequest request) throws Exception {
        if (movieRepository.findByTitle(request.getTitle()) != null) {
            throw new Exception("Tittle is already in use");
        }
    }
}
