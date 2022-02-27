package com.roerdev.moviesapi.repository;

import com.roerdev.moviesapi.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findByEmail(String email);

    @Query("select u from UserEntity u")
    Page<UserEntity> findAll(Pageable pageable);
}
