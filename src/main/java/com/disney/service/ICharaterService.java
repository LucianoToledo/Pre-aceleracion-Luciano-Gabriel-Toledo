package com.disney.service;

import com.disney.dto.CharacterBasicDTO;
import com.disney.dto.CharacterDTO;

public interface ICharaterService {
    CharacterDTO save(CharacterBasicDTO request);

    CharacterDTO update(CharacterDTO request);

    void disableCharacter(String id) throws Exception;

    void enableCharacter(String id) throws Exception;
}
