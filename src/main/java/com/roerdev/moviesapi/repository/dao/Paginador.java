package com.roerdev.moviesapi.repository.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class Paginador {

  public Page<?> paginate(List<?> l, Pageable pageable, Class<?> t, EntityManager em) {
    return PageableExecutionUtils.getPage(l, pageable, () -> this.getCountForQuery(t, em));
  }

  public Long getCountForQuery(Class<?> t, EntityManager em) {

    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

    CriteriaQuery<Long> countQuery = criteriaBuilder
            .createQuery(Long.class);
    countQuery.select(criteriaBuilder.count(
            countQuery.from(t)));
    Long count = em.createQuery(countQuery)
            .getSingleResult();

    return count;
  }
}
