package com.roerdev.moviesapi.service;

import com.roerdev.moviesapi.dto.MovieDetailDto;
import com.roerdev.moviesapi.dto.MovieDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MovieService {

    MovieDetailDto getMovie(UUID id) throws Exception;
    Page<MovieDto> getAllMovies(String name, UUID genre, String order, Pageable paginador) throws Exception;
    MovieDto saveMovie(MovieDetailDto movie) throws Exception;
    MovieDto updateMovie(UUID id, MovieDetailDto movie) throws Exception;
    UUID deleteMovie(UUID id) throws Exception;
}
