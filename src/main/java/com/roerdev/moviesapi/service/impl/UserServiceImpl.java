package com.roerdev.moviesapi.service.impl;

import com.roerdev.moviesapi.config.SendEmailConfig;
import com.roerdev.moviesapi.dto.UserDto;
import com.roerdev.moviesapi.email.WelcomeEmailNotification;
import com.roerdev.moviesapi.models.UserEntity;
import com.roerdev.moviesapi.repository.UserRepository;
import com.roerdev.moviesapi.service.UserService;
import com.roerdev.moviesapi.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendEmailConfig rappiConfig;
    private final WelcomeEmailNotification notification;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email);
        if(user == null){
            log.error("Usuario no encontrado.");
            throw new UsernameNotFoundException("Usuario no encontrado.");
        }
        return new User(user.getEmail(), user.getPassword(), Constants.roles(1));

    }

    @Override
    public UserDto saveUser(UserDto userDto) throws Exception {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity entity = userDto.toEntity();
        entity.setCreatedDate(LocalDateTime.now());
        var newUser = userRepository.save(entity);
        try {
            notification.sendEmail(newUser, rappiConfig);
        }catch (IOException ex){
            log.error("{}", ex.getMessage());
            throw new RuntimeException("Error de envio de correo");
        }
        return modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto getUser(String email) {
        var user = userRepository.findByEmail(email);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Page<UserDto> getAllUser(Pageable page) {
        var allUser = userRepository.findAll(page);
        return allUser.map(userEntity -> modelMapper.map(userEntity, UserDto.class));
    }

}
