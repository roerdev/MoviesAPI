package com.roerdev.moviesapi.repository;

import com.roerdev.moviesapi.models.GenreEntity;
import com.roerdev.moviesapi.models.MovieGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MovieGenreRepository extends JpaRepository<MovieGenreEntity, UUID> {

    List<MovieGenreEntity> findAllByGenre(GenreEntity genre);
    @Query("select m from MovieGenreEntity m where m.movie.id = ?1")
    List<MovieGenreEntity> findAllByMovie(UUID movie);

}
