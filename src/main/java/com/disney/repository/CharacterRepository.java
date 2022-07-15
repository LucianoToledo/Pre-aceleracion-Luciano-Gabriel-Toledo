package com.disney.repository;

import com.disney.entity.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, String> {

    CharacterEntity findByIdAndSoftDeleteFalse(String id);

    CharacterEntity findByName(String name);

}
/*
    @Query(value = "SELECT * FROM Movie m WHERE m.id = (SELECT movie_id FROM movie_character WHERE character_id LIKE :characterId);", nativeQuery = true)
    List<MovieEntity> getMoviesByCharacter(@Param("characterId) String characterId));

            @Query(value = "SELECT m FROM CharacterEntity c, MovieEntity m WHERE m.characters = c")
            List<MovieEntity>getMoviesByCharacter();
            https://www.baeldung.com/jpa-join-types*/
