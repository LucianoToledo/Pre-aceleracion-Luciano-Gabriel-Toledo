package com.disney.service;

import com.disney.dto.request.CharacterRequest;
import com.disney.dto.response.CharacterBasicResponse;
import com.disney.dto.response.CharacterResponse;
import com.disney.entity.CharacterEntity;
import com.disney.exception.EntityNotFound;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

public interface ICharacterService {
    CharacterResponse save(CharacterRequest request) throws Exception;

    CharacterResponse update(CharacterRequest request) throws Exception;

    CharacterResponse enableCharacter(String id) throws Exception;

    CharacterResponse disableCharacter(String id) throws Exception;

    CharacterEntity getByIdAndSoftDeleteFalse(String id) throws EntityNotFoundException;

    CharacterEntity getById(String id) throws EntityNotFoundException;

    List<CharacterResponse> getAll();

    List<CharacterBasicResponse> getByName(String name) throws EntityNotFound;

    List<CharacterBasicResponse> getByAge(String age) throws EntityNotFound;

    List<CharacterBasicResponse> getByMovieId(String movieId) throws EntityNotFound;

    List<CharacterBasicResponse> getByQuery(Map<String, String> modelMap) throws EntityNotFound;


}
