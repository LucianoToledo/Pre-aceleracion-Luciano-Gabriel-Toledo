package com.disney.repository;

import com.disney.dto.request.MovieRequest;
import com.disney.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, String>, JpaSpecificationExecutor<MovieEntity> {
    MovieEntity findByIdAndSoftDeleteFalse(String id);
    MovieEntity findByTitle(String tittle);
    List<MovieEntity> findAll(Specification<MovieEntity> spec);
    //@Query(value = "SELECT * FROM movie WHERE title LIKE %:title%",nativeQuery = true)
    //@Query("SELECT m FROM MovieEntity m WHERE m.title LIKE %:title%")

    @Query(value = "SELECT * FROM movie m, movie_character mc WHERE m.id = mc.movie_id AND m.title LIKE %:title%",nativeQuery = true)
    List<MovieEntity> findByTitleAndSoftDeleteFalse(@Param("title") String title);



}
