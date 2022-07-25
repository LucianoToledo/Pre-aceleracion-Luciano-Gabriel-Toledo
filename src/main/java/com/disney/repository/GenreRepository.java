package com.disney.repository;

import com.disney.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, String> {
    GenreEntity findByNameAndSoftDeleteFalse(String name);
    GenreEntity findByIdAndSoftDeleteFalse(String id);
}
