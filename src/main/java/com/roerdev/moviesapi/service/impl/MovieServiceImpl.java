package com.roerdev.moviesapi.service.impl;

import com.roerdev.moviesapi.dto.CharacterDto;
import com.roerdev.moviesapi.dto.GenreDto;
import com.roerdev.moviesapi.dto.MovieDto;
import com.roerdev.moviesapi.dto.MovieDetailDto;
import com.roerdev.moviesapi.exceptions.NotFoundException;
import com.roerdev.moviesapi.models.*;
import com.roerdev.moviesapi.repository.*;
import com.roerdev.moviesapi.repository.dao.MovieDao;
import com.roerdev.moviesapi.service.MovieService;
import com.roerdev.moviesapi.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final ModelMapper modelMapper;
    private final MovieRepository movieRepository;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieGenreRepository movieGenreRepository;
    private final GenreRepository genreRepository;
    private final CharacterRepository characterRepository;
    private final MovieDao movieDao;

    @Override
    public MovieDetailDto getMovie(UUID id) throws Exception {
        var movie = this.movieRepository.findByIdActive(id).orElseThrow(() -> {
            log.error("No se encontro registro para el ID: {}", id.toString());
            throw new NotFoundException("No se encontro registro para el ID "+ id);
        });
        var dtoDetail = new MovieDetailDto(movie);
        List<MovieCharacterEntity> allByMovie = movieCharacterRepository.findAllByMovie(movie.getId());
        allByMovie.stream().map(MovieCharacterEntity::getCharacter)
                .forEach(character ->
                    dtoDetail.getCharacters().add(this.modelMapper.map(character, CharacterDto.class))
                );
        List<MovieGenreEntity> allByMovieGenre = movieGenreRepository.findAllByMovie(movie.getId());
        allByMovieGenre.stream()
                .map(MovieGenreEntity::getGenre).forEach(genre ->
                    dtoDetail.getGenres().add(modelMapper.map(genre, GenreDto.class))
                );
        return dtoDetail;
    }

    @Override
    public Page<MovieDto> getAllMovies(String name, UUID genre, String order, Pageable paginador) throws Exception {
        return this.movieDao.getAllWithFilter(name, genre, order, paginador);
    }

    @Override
    public MovieDto saveMovie(MovieDetailDto movie) throws Exception {
        var entity = this.modelMapper.map(movie, MovieEntity.class);
        entity.setActive(Constants.ACTIVE);
        MovieEntity save = this.movieRepository.save(entity);
        movie.getGenres().stream().forEach(genreDto -> {
            var genreE = this.genreRepository.findByIdActive(genreDto.getId()).get();
            MovieGenreEntity movieGenreEntity = MovieGenreEntity.builder()
                    .genre(genreE)
                    .movie(save)
                    .build();
            this.movieGenreRepository.save(movieGenreEntity);
        });
        movie.getCharacters().stream().forEach(characterDto -> {
            var character = this.characterRepository.findByIdActive(characterDto.getId()).get();
            MovieCharacterEntity movieCharEntity = MovieCharacterEntity.builder()
                    .character(character)
                    .movie(save)
                    .build();
            this.movieCharacterRepository.save(movieCharEntity);
        });
        return this.modelMapper.map(save, MovieDto.class);
    }

    @Override
    public MovieDto updateMovie(UUID id, MovieDetailDto movie) throws Exception {
        var entity = this.movieRepository.findByIdActive(id).orElseThrow(() -> {
            log.error("No se encontro registro para el ID: {}", id.toString());
            throw new NotFoundException("No se encontro registro para el ID "+ id);
        });

        entity.setImage(movie.getImage() != null ? movie.getImage() : entity.getImage());
        entity.setTitle(movie.getTitle() != null ? movie.getTitle() : entity.getTitle());
        entity.setRatings(movie.getRatings() != null ? movie.getRatings() : entity.getRatings());
        entity.setCreatedDate(movie.getCreatedDate() != null ? movie.getCreatedDate() : entity.getCreatedDate());

        return this.modelMapper.map(this.movieRepository.save(entity), MovieDto.class);
    }

    @Override
    public UUID deleteMovie(UUID id) throws Exception {
        var entity = this.movieRepository.findByIdActive(id).orElseThrow(() -> {
            log.error("No se encontro registro para el ID: {}", id.toString());
            throw new NotFoundException("No se encontro registro para el ID "+ id);
        });
        entity.setActive(Constants.INACTIVE);
        var newEntity = this.movieRepository.save(entity);

        return newEntity.getId();
    }
}
