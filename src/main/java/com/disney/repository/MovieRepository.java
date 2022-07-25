package com.disney.repository;

import com.disney.dto.response.CharacterBasicResponse;
import com.disney.dto.response.MovieBasicResponse;
import com.disney.entity.MovieEntity;
import com.disney.exception.EntityNotFound;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, String>, JpaSpecificationExecutor<MovieEntity> {
    MovieEntity findByIdAndSoftDeleteFalse(String id);

    @Query(value = "SELECT * FROM movie ORDER BY title ASC", nativeQuery = true)
    List<MovieEntity> findAll();

    @Query(value = "SELECT * FROM movie WHERE title LIKE %:title% ORDER BY title ASC", nativeQuery = true)
    List<Optional<MovieEntity>> findByTitleAsc(@Param("title") String title);

    @Query(value = "SELECT * FROM movie  WHERE title LIKE %:title% ORDER BY title DESC", nativeQuery = true)
    List<Optional<MovieEntity>> findByTitleDesc(@Param("title") String title);

    @Query(value = "SELECT * FROM movie WHERE genre_id LIKE :genreId ORDER BY title ASC", nativeQuery = true)
    List<Optional<MovieEntity>> findByGenreAsc(@Param("genreId") String genreId);

    @Query(value = "SELECT * FROM movie WHERE genre_id LIKE :genreId ORDER BY title DESC", nativeQuery = true)
    List<Optional<MovieEntity>> findByGenreDesc(@Param("genreId") String genreId);
    MovieEntity findByTitle(String title);
}
