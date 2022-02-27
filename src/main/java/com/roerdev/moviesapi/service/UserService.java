package com.roerdev.moviesapi.service;

import com.roerdev.moviesapi.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserDto saveUser (UserDto userDto) throws Exception;
    UserDto getUser (String email);
    Page<UserDto> getAllUser (Pageable page);
}
