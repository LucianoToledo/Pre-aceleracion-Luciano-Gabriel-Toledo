package com.disney.mapper;

import com.disney.dto.CharacterBasicDTO;
import com.disney.dto.CharacterDTO;
import com.disney.entity.CharacterEntity;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper {

    public CharacterEntity map(CharacterBasicDTO request){ //requestBasic to entityDto
        CharacterEntity character = new CharacterEntity();
        character.setName(request.getName());
        character.setAge(request.getAge());
        character.setWeight(request.getWeight());
        character.setHistory(request.getHistory());
        character.setImage(request.getImage());
        return character;
    }

    public CharacterDTO map(CharacterEntity entity){ //entity to responseDto
        CharacterDTO response = new CharacterDTO();
        response.setId(entity.getCharacterId());
        response.setName(entity.getName());
        response.setAge(entity.getAge());
        response.setWeight(entity.getWeight());
        response.setHistory(entity.getHistory());
        response.setImage(entity.getImage());
        return response;
    }

    public CharacterEntity mapUpdate(CharacterDTO request, CharacterEntity character){
        character.setName(request.getName());
        character.setAge(request.getAge());
        character.setWeight(request.getWeight());
        character.setHistory(request.getHistory());
        character.setImage(request.getImage());
        return character;
    }

}
