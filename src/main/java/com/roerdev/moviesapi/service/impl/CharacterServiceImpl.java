package com.roerdev.moviesapi.service.impl;

import com.roerdev.moviesapi.dto.CharacterDetailDto;
import com.roerdev.moviesapi.dto.CharacterDto;
import com.roerdev.moviesapi.dto.MovieDto;
import com.roerdev.moviesapi.exceptions.NotFoundException;
import com.roerdev.moviesapi.models.CharacterEntity;
import com.roerdev.moviesapi.models.MovieCharacterEntity;
import com.roerdev.moviesapi.repository.CharacterRepository;
import com.roerdev.moviesapi.repository.MovieCharacterRepository;
import com.roerdev.moviesapi.repository.MovieRepository;
import com.roerdev.moviesapi.repository.dao.CharacterDao;
import com.roerdev.moviesapi.service.CharacterService;
import com.roerdev.moviesapi.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final ModelMapper modelMapper;
    private final CharacterRepository characterRepository;
    private final MovieRepository movieRepository;
    private final CharacterDao characterDao;
    private final EntityManager em;
    private final MovieCharacterRepository movieCharacterRepository;

    @Override
    public CharacterDetailDto getCharacter(UUID id) throws Exception {
        var characterEntity = this.characterRepository.findByIdActive(id).orElseThrow(() -> {
            log.error("No se encontro registro para el ID: {}", id.toString());
            throw new NotFoundException("No se encontro registro para el ID "+ id.toString());
        });
        var characterDetailDto = modelMapper.map(characterEntity, CharacterDetailDto.class);
        if(characterDetailDto.getMoviesDto() == null){
            characterDetailDto.setMoviesDto(new ArrayList<>());
        }
        List<MovieCharacterEntity> allByCharacter = movieCharacterRepository.findAllByCharacter(characterEntity.getId());
        allByCharacter.stream().map(MovieCharacterEntity::getMovie)
                .forEach(movie ->
                    characterDetailDto.getMoviesDto().add(this.modelMapper.map(movie, MovieDto.class))
                );
        return characterDetailDto;
    }

    @Override
    public Page<CharacterDto> getAllCharacters(String name, Integer age, UUID movie, Pageable paginador) throws Exception {
        return this.characterDao.getAllWithFilter(name, age, movie, paginador);
    }

    @Override
    public CharacterDto saveCharacter(CharacterDetailDto character) throws Exception {
        var entity = this.modelMapper.map(character, CharacterEntity.class);
        entity.setActive(Constants.ACTIVE);
        CharacterEntity save = this.characterRepository.save(entity);
        character.getMoviesDto().stream().forEach(movieDto -> {
            var movieOp = this.movieRepository.findByIdActive(movieDto.getId());
            if(movieOp.isPresent()){
                MovieCharacterEntity movieCharEntity = MovieCharacterEntity.builder()
                        .character(save)
                        .movie(movieOp.get())
                        .build();
                this.movieCharacterRepository.save(movieCharEntity);
            }
        });
        return this.modelMapper.map(save, CharacterDto.class);
    }

    @Override
    public CharacterDto updateCharacter(UUID id, CharacterDetailDto character) throws Exception {
        var entity = this.characterRepository.findByIdActive(id).orElseThrow(() -> {
            log.error("No se encontro registro para el ID: {}", id.toString());
            throw new NotFoundException("No se encontro registro para el ID "+ id.toString());
        });

        entity.setImage(character.getImage() != null ? character.getImage() : entity.getImage());
        entity.setName(character.getName() != null ? character.getName() : entity.getName());
        entity.setAge(character.getAge() != null ? character.getAge() : entity.getAge());
        entity.setWeight(character.getWeight() != null ? character.getWeight() : entity.getWeight());
        entity.setHistory(character.getHistory() != null ? character.getHistory() : entity.getHistory());

        return this.modelMapper.map(this.characterRepository.save(entity), CharacterDto.class);
    }

    @Override
    public UUID deleteCharacter(UUID id) throws Exception {
        var entity = this.characterRepository.findByIdActive(id).orElseThrow(() -> {
            log.error("No se encontro registro para el ID: {}", id.toString());
            throw new NotFoundException("No se encontro registro para el ID "+ id.toString());
        });
        entity.setActive(Constants.INACTIVE);
        var newEntity = this.characterRepository.save(entity);

        return newEntity.getId();
    }
}
