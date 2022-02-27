package com.roerdev.moviesapi.repository;

import com.roerdev.moviesapi.models.MovieCharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacterEntity, UUID> {


    @Query("select m from MovieCharacterEntity m where m.character.id = ?1")
    List<MovieCharacterEntity> findAllByCharacter(UUID id);
    @Query("select m from MovieCharacterEntity m where m.movie.id = ?1")
    List<MovieCharacterEntity> findAllByMovie(UUID id);
}
