package com.roerdev.moviesapi.repository.dao;

import com.roerdev.moviesapi.dto.MovieDto;
import com.roerdev.moviesapi.models.*;
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
public class MovieDao {

    private final EntityManager em;
    private final ModelMapper modelMapper;

    public Page<MovieDto> getAllWithFilter(String name, UUID genre, String order, Pageable pageable){
        var criteriaBuilder = em.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(MovieEntity.class);
        var rootMovie = criteriaQuery.from(MovieEntity.class);
        Join<MovieGenreEntity, MovieEntity> joinMovies = rootMovie.join("movieGenres", JoinType.LEFT);

        var predicates = new ArrayList<Predicate>();

        if (!(name == null || name.isEmpty())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(rootMovie.get("title")), "%"+name.toLowerCase()+"%"));
        }
        if(genre != null){
            predicates.add(criteriaBuilder.equal(joinMovies.get("genre").get("id"), genre));
        }

        predicates.add(criteriaBuilder.equal(rootMovie.get("active"), 1));

        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }

        if (!(order == null || order.isEmpty())) {
            if (order.equalsIgnoreCase("asc")) {
                criteriaQuery.orderBy(criteriaBuilder.asc(rootMovie.get("title")));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(rootMovie.get("title")));
            }
        }

        long offset = pageable.getOffset();
        var result = em.createQuery(criteriaQuery)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult((int) offset)
                .getResultList();
        var paginador = new Paginador();
        var listDTO = result.stream()
                .map(movie -> this.modelMapper.map(movie, MovieDto.class))
                .toList();

        return (Page<MovieDto>) paginador.paginate(listDTO, pageable, MovieDto.class, em);
    }
}
