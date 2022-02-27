package com.roerdev.moviesapi.controllers;

import com.roerdev.moviesapi.dto.ResponseDTO;
import com.roerdev.moviesapi.dto.UserDto;
import com.roerdev.moviesapi.service.UserService;
import com.roerdev.moviesapi.utils.Constants;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/register")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Servicio para guardar un nuevo usuario")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> saveUser(@RequestBody UserDto user){

        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.CREATED.getReasonPhrase(),
                    this.userService.saveUser(user),
                    Constants.MESSAGE
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{}", e.getMessage());
            throw new RuntimeException("Failed to save.");
        }
    }

}
