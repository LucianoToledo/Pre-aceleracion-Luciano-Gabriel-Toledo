package com.disney.repository;

import com.disney.dto.request.MovieRequest;
import com.disney.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, String>, JpaSpecificationExecutor<MovieEntity> {
    MovieEntity findByIdAndSoftDeleteFalse(String id);
    MovieEntity findByTitle(String tittle);
    List<MovieEntity> findAll(Specification<MovieEntity> spec);
}
