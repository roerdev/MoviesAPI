package com.roerdev.moviesapi.service;

import com.roerdev.moviesapi.dto.GenreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GenreService {

     GenreDto getGenre(UUID id) throws Exception;
     Page<GenreDto> getAllGenres(Pageable paginador) throws Exception;
     GenreDto saveGenre(GenreDto genre) throws Exception;
     GenreDto updateGenre(UUID id, GenreDto genre) throws Exception;
     UUID deleteGenre(UUID id) throws Exception;
}
