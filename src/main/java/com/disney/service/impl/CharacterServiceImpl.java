package com.disney.service.impl;

import com.disney.dto.request.CharacterRequest;
import com.disney.dto.response.CharacterBasicResponse;
import com.disney.dto.response.CharacterResponse;
import com.disney.entity.CharacterEntity;
import com.disney.exception.EntityNotFound;
import com.disney.mapper.CharacterMapper;
import com.disney.mapper.MovieMapper;
import com.disney.repository.CharacterRepository;
import com.disney.service.ICharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class CharacterServiceImpl implements ICharacterService {

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    CharacterMapper characterMapper;

    @Autowired
    MovieMapper movieMapper;

    @Override
    public CharacterResponse save(CharacterRequest request) throws Exception {
        validateRequest(request);
        return characterMapper.map(characterRepository.save(characterMapper.map(request)));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CharacterResponse update(CharacterRequest request) throws Exception {
        CharacterEntity entity = getByIdAndSoftDeleteFalse(request.getId());
        entity.setName(request.getName());
        entity.setAge(request.getAge());
        entity.setWeight(request.getWeight());
        entity.setHistory(request.getHistory());
        entity.setImage(request.getImage());
        return characterMapper.map(characterRepository.save(entity), true);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CharacterResponse enableCharacter(String id) throws Exception {
        CharacterEntity entity = getById(id);
        if (entity.isEnabled()) {
            throw new Exception("character is already enable");
        }
        entity.setSoftDelete(false);
        return characterMapper.map(characterRepository.save(entity));

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CharacterResponse disableCharacter(String id) throws Exception {
        CharacterEntity entity = getById(id);
        if (!entity.isEnabled()) {
            throw new Exception("character is already disable");
        }
        entity.setSoftDelete(true);
        return characterMapper.map(characterRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public CharacterEntity getByIdAndSoftDeleteFalse(String id) throws EntityNotFoundException {
        Optional<CharacterEntity> opt = Optional.ofNullable(characterRepository.findByIdAndSoftDeleteFalse(id));
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Character not found or disable");
        }
        return opt.get();
    }

    @Override
    @Transactional(readOnly = true)
    public CharacterEntity getById(String id) throws EntityNotFoundException {
        Optional<CharacterEntity> opt = characterRepository.findById(id);
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Character not found or disable");
        }
        return opt.get();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<CharacterResponse> getAll() {
        List<CharacterResponse> characterResponseList = new ArrayList<>();
        CharacterResponse response;
        List<CharacterEntity> entityList = characterRepository.findAll();
        for (CharacterEntity entity : entityList) {
            response = characterMapper.map(entity);
            response.setMovies(movieMapper.map(entity.getMovies()));
            characterResponseList.add(response);
        }
        return characterResponseList;
    }

    private List<CharacterEntity> getOptional(List<Optional<CharacterEntity>> optList) throws EntityNotFound {
        List<CharacterEntity> entityList = new ArrayList<>();
        if (optList.isEmpty()) {
            throw new EntityNotFound("Characters not found or disabled");
        }
        for (Optional<CharacterEntity> opt : optList) {
            entityList.add(opt.get());
        }
        return entityList;
    }

    @Override
    @Transactional
    public List<CharacterBasicResponse> getByName(String name) throws EntityNotFound {
        List<Optional<CharacterEntity>> opt = characterRepository.findByName(name);
        return characterMapper.mapBasic(getOptional(opt));
    }

    @Override
    @Transactional
    public List<CharacterBasicResponse> getByAge(String age) throws EntityNotFound {
        List<Optional<CharacterEntity>> opt = characterRepository.findByAge(Integer.parseInt(age));
        return characterMapper.mapBasic(getOptional(opt));
    }

    @Override
    @Transactional
    public List<CharacterBasicResponse> getByMovieId(String movieId) throws EntityNotFound {
        List<String> charactersId = characterRepository.findByMovieId(movieId);
        List<Optional<CharacterEntity>> entities = new ArrayList<>();
        for (String characterId : charactersId) {
            entities.add(Optional.ofNullable(getById(characterId)));
        }
        return characterMapper.mapBasic(getOptional(entities));
    }

    @Override
    @Transactional
    public List<CharacterBasicResponse> getByQuery(Map<String, String> modelMap) throws EntityNotFound {
        List<CharacterBasicResponse> responseList = new ArrayList<>();
        if (modelMap.isEmpty()) {
            return characterMapper.mapResponse2basic(getAll());
        }
        for (Map.Entry entry : modelMap.entrySet()) {
            if (entry.getKey().toString().equalsIgnoreCase("name")) {
                responseList.addAll(getByName(entry.getValue().toString()));
            }
            if (entry.getKey().toString().equalsIgnoreCase("age")) {
                responseList.addAll(getByAge(entry.getValue().toString()));
            }
            if (entry.getKey().toString().equalsIgnoreCase("movie")) {
                responseList.addAll(getByMovieId(entry.getValue().toString()));
            }
        }
        HashSet<String> withOutDuplicates = new HashSet<>();
        responseList.removeIf(e -> !withOutDuplicates.add(e.getName()));
        return responseList;
    }

    @Transactional(readOnly = true)
    private void validateRequest(CharacterRequest request) throws Exception {
        if (characterRepository.getByName(request.getName()) != null) {
            throw new Exception("Name is already in use");
        }
        if (request.getAge() <= 0) {
            throw new Exception("Age cannot be less than or equal to zero");
        }
        if (request.getWeight() <= 0) {
            throw new Exception("Weight cannot be less than or equal to zero");
        }
    }
}
