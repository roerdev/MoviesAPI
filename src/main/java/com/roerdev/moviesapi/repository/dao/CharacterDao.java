package com.roerdev.moviesapi.repository.dao;

import com.roerdev.moviesapi.dto.CharacterDto;
import com.roerdev.moviesapi.models.CharacterEntity;
import com.roerdev.moviesapi.models.MovieCharacterEntity;
import com.roerdev.moviesapi.models.MovieEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CharacterDao {

    private final EntityManager em;
    private final ModelMapper modelMapper;

    public Page<CharacterDto> getAllWithFilter(String name, Integer age, UUID movie, Pageable pageable){
        var criteriaBuilder = em.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(MovieCharacterEntity.class);
        var rootCharacter = criteriaQuery.from(MovieCharacterEntity.class);
        Join<MovieCharacterEntity, MovieEntity> joinMovies = rootCharacter.join("movie", JoinType.LEFT);
        Join<MovieCharacterEntity, CharacterEntity> joinCharacter = rootCharacter.join("character", JoinType.LEFT);

        var predicates = new ArrayList<Predicate>();

        if (!(name == null || name.isEmpty())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(joinCharacter.get("name")), "%"+name.toLowerCase()+"%"));
        }
        if(age != null){
            predicates.add(criteriaBuilder.equal(joinCharacter.get("age"), age));
        }
        if(movie != null){
            predicates.add(criteriaBuilder.equal(joinMovies.get("id"), movie));
        }

        predicates.add(criteriaBuilder.equal(joinCharacter.get("active"), 1));

        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }

        long offset = pageable.getOffset();
        var result = em.createQuery(criteriaQuery)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult((int) offset)
                .getResultList();
        var paginador = new Paginador();
        var listDTO = result.stream().map(MovieCharacterEntity::getCharacter)
                .map(characterEntity -> this.modelMapper.map(characterEntity, CharacterDto.class))
                .toList();
        return (Page<CharacterDto>) paginador.paginate(listDTO, pageable, CharacterDto.class, em);
    }
}
