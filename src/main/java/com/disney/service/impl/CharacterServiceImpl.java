package com.disney.service.impl;

import com.disney.dto.request.CharacterRequest;
import com.disney.dto.response.CharacterBasicResponse;
import com.disney.dto.response.CharacterResponse;
import com.disney.entity.CharacterEntity;
import com.disney.mapper.CharacterMapper;
import com.disney.mapper.MovieMapper;
import com.disney.repository.CharacterRepository;
import com.disney.service.ICharaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements ICharaterService {

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
    public CharacterResponse update(CharacterRequest request) {
        CharacterEntity entity = getByIdAndSoftDeleteFalse(request.getId());
        entity.setName(request.getName());
        entity.setAge(request.getAge());
        entity.setWeight(request.getWeight());
        entity.setHistory(request.getHistory());
        entity.setImage(request.getImage());
        return characterMapper.map(characterRepository.save(entity));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void enableCharacter(String id) throws Exception {
        CharacterEntity entity = getById(id);
        if (entity.isEnabled()) {
            throw new Exception("character is already enable");
        }
        entity.setSoftDelete(false);
        characterRepository.save(entity);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void disableCharacter(String id) throws Exception {
        CharacterEntity entity = getById(id);
        if (!entity.isEnabled()) {
            throw new Exception("character is already disable");
        }
        entity.setSoftDelete(true);
        characterRepository.save(entity);
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

    @Override
    @Transactional
    public List<CharacterBasicResponse> getByName(String name) {
        return characterMapper.mapBasic(characterRepository.findByName(name));
    }

    @Override
    @Transactional
    public List<CharacterBasicResponse> getByAge(String age) {
        return characterMapper.mapBasic(characterRepository.findByAge(Integer.parseInt(age)));
    }

    @Override
    @Transactional
    public List<CharacterBasicResponse> getByMovieId(String movieId) {
        List<String> charactersId = characterRepository.findByMovieId(movieId);
        List<CharacterEntity> entities = new ArrayList<>();
        for (String characterId : charactersId) {
            entities.add(getById(characterId));
        }
        return characterMapper.mapBasic(entities);
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
