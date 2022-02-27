package com.roerdev.moviesapi.service.impl;

import com.roerdev.moviesapi.dto.GenreDto;
import com.roerdev.moviesapi.exceptions.NotFoundException;
import com.roerdev.moviesapi.models.GenreEntity;
import com.roerdev.moviesapi.repository.GenreRepository;
import com.roerdev.moviesapi.service.GenreService;
import com.roerdev.moviesapi.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;

    @Override
    public GenreDto getGenre(UUID id) throws Exception {
        var genre = this.genreRepository.findByIdActive(id).orElseThrow(() -> {
            log.error("No se encontro registro para el ID: {}", id.toString());
            throw new NotFoundException("No se encontro registro para el ID "+ id.toString());
        });
        return modelMapper.map(genre, GenreDto.class);
    }

    @Override
    public Page<GenreDto> getAllGenres(Pageable paginador) throws Exception {
        var allGenre = this.genreRepository.findAllByActive(Constants.ACTIVE, paginador);
        return allGenre.map(genreEntity -> this.modelMapper.map(genreEntity, GenreDto.class));
    }

    @Override
    public GenreDto saveGenre(GenreDto genre) throws Exception {
        var entity = this.modelMapper.map(genre, GenreEntity.class);
        entity.setActive(Constants.ACTIVE);
        return this.modelMapper.map(this.genreRepository.save(entity), GenreDto.class);
    }

    @Override
    public GenreDto updateGenre(UUID id, GenreDto genre) throws Exception {
        var entity = this.genreRepository.findByIdActive(id).orElseThrow(() -> {
            log.error("No se encontro registro para el ID: {}", id.toString());
            throw new NotFoundException("No se encontro registro para el ID "+ id.toString());
        });

        entity.setImage(genre.getImage() != null ? genre.getImage() : entity.getImage());
        entity.setName(genre.getName() != null ? genre.getName() : entity.getName());

        return this.modelMapper.map(this.genreRepository.save(entity), GenreDto.class);
    }

    @Override
    public UUID deleteGenre(UUID id) throws Exception {
        var entity = this.genreRepository.findByIdActive(id).orElseThrow(() -> {
            log.error("No se encontro registro para el ID: {}", id.toString());
            throw new NotFoundException("No se encontro registro para el ID "+ id.toString());
        });
        entity.setActive(Constants.INACTIVE);
        var newEntity = this.genreRepository.save(entity);

        return newEntity.getId();
    }
}
