package com.roerdev.moviesapi.repository;

import com.roerdev.moviesapi.models.GenreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<GenreEntity, UUID> {

    @Query("select ue from GenreEntity ue where ue.id = ?1 and ue.active = 1")
    Optional<GenreEntity> findByIdActive(UUID id);

    @Query("select ue from GenreEntity ue where ue.active = ?1")
    Page<GenreEntity> findAllByActive(int active, Pageable pageable);
}
