package com.roerdev.moviesapi.service;

import com.roerdev.moviesapi.dto.CharacterDetailDto;
import com.roerdev.moviesapi.dto.CharacterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CharacterService {

    CharacterDetailDto getCharacter(UUID id) throws Exception;
    Page<CharacterDto> getAllCharacters(String name, Integer age, UUID movie, Pageable paginador) throws Exception;
    CharacterDto saveCharacter(CharacterDetailDto characterDto) throws Exception;
    CharacterDto updateCharacter(UUID id, CharacterDetailDto character) throws Exception;
    UUID deleteCharacter(UUID id) throws Exception;
}
