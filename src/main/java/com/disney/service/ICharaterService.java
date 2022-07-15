package com.disney.service;

import com.disney.dto.request.CharacterRequest;
import com.disney.dto.response.CharacterResponse;
import com.disney.entity.CharacterEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface ICharaterService {
    CharacterResponse save(CharacterRequest request) throws Exception;

    CharacterResponse update(CharacterRequest request);
    void enableCharacter(String id) throws Exception;

    void disableCharacter(String id) throws Exception;

    CharacterEntity getByIdAndSoftDeleteFalse(String id) throws EntityNotFoundException;

    CharacterEntity getById(String id) throws EntityNotFoundException;

    List<CharacterResponse> getAll();
}
