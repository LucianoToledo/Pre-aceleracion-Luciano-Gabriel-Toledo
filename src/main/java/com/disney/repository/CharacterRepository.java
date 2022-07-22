package com.disney.repository;

import com.disney.entity.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, String> {

    CharacterEntity findByIdAndSoftDeleteFalse(String id);

    CharacterEntity getByName(String name);
    @Query(value = "SELECT c FROM CharacterEntity c WHERE c.name LIKE %:name%")
    List<CharacterEntity> findByName(@Param("name") String name);

    List<CharacterEntity> findByAge(Integer age);

    @Query(value = "SELECT character_id FROM movie_character WHERE movie_id LIKE :movieId", nativeQuery = true)
    List<String> findByMovieId(@Param("movieId") String movieId);
}
