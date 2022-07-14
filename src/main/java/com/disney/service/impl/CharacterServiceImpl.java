package com.disney.service.impl;

import com.disney.dto.CharacterBasicDTO;
import com.disney.dto.CharacterDTO;
import com.disney.entity.CharacterEntity;
import com.disney.mapper.CharacterMapper;
import com.disney.repository.CharacterRepository;
import com.disney.service.ICharaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements ICharaterService {

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    CharacterMapper characterMapper;

    @Override
    public CharacterDTO save(CharacterBasicDTO request) {
        return characterMapper.map(characterRepository.save(characterMapper.map(request)));
    }

    @Override
    public CharacterDTO update(CharacterDTO request) {
        return characterMapper.map(characterRepository.save(characterMapper.mapUpdate(request, getByIdAndSoftDeleteFalse(request.getId()))));
    }

    @Transactional(rollbackFor = {Exception.class})
    public void enableCharacter(String id) throws Exception {
        CharacterEntity character =characterRepository.findByCharacterId(id);
        if (character.isEnabled()) {
            throw new Exception("character is already enable");
        }
        character.setSoftDelete(false);
        characterRepository.save(character);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void disableCharacter(String id) throws Exception {
        CharacterEntity character =characterRepository.findByCharacterId(id);
        if (!character.isEnabled()) {
            throw new Exception("character is already enable");
        }
        character.setSoftDelete(true);
        characterRepository.save(character);
    }

    private CharacterEntity getByIdAndSoftDeleteFalse(String id) {
        Optional<CharacterEntity> opt = Optional.ofNullable(characterRepository.findByCharacterIdAndSoftDeleteFalse(id));
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Character not found or disable");
        }
        return opt.get();
    }

}
