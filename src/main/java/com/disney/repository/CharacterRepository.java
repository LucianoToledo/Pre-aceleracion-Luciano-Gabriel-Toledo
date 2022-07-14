package com.disney.repository;

import com.disney.entity.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, String> {

    CharacterEntity findByCharacterId(String id);

    CharacterEntity findByCharacterIdAndSoftDeleteFalse(String id);
}
