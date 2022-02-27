package com.roerdev.moviesapi.repository;

import com.roerdev.moviesapi.models.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<MovieEntity, UUID> {

    @Query("select mov from MovieEntity mov where mov.id = ?1 and mov.active = 1")
    Optional<MovieEntity> findByIdActive(UUID id);

    @Query("select mov from MovieEntity mov where mov.active = ?1")
    Page<MovieEntity> findAllByActive(int active, Pageable pageable);
}
