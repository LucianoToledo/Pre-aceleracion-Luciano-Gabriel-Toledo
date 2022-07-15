package com.disney.repository;

import com.disney.dto.request.MovieRequest;
import com.disney.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, String> {
    MovieEntity findByIdAndSoftDeleteFalse(String id);

    MovieEntity findByTitle(String tittle);
}
