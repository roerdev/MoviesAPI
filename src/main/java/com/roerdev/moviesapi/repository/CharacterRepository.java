package com.roerdev.moviesapi.repository;

import com.roerdev.moviesapi.models.CharacterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CharacterRepository extends JpaRepository<CharacterEntity, UUID> {

    @Query("select cha from CharacterEntity cha where cha.id = ?1 and cha.active = 1")
    Optional<CharacterEntity> findByIdActive(UUID id);

    @Query("select cha from CharacterEntity cha where cha.active = ?1")
    Page<CharacterEntity> findAllByActive(int active, Pageable pageable);
}
