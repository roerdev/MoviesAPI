package com.roerdev.moviesapi.controllers;

import com.roerdev.moviesapi.dto.GenreDto;
import com.roerdev.moviesapi.dto.ResponseDTO;
import com.roerdev.moviesapi.service.GenreService;
import com.roerdev.moviesapi.utils.Constants;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    @ApiOperation(value = "Servicio para obtener un genero por ID")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getGenre (@PathVariable UUID id){

        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    this.genreService.getGenre(id),
                    Constants.MESSAGE
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Not founds.");
        }
    }

    @ApiOperation(value = "Servicio para obtener todos los generos registrados")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAllGenre(Pageable paginador) {
        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    this.genreService.getAllGenres(paginador),
                    Constants.MESSAGE
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("");
        }
    }

    @ApiOperation(value = "Servicio para guardar un nuevo genero")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> saveGenre(@RequestBody GenreDto genre){

        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    this.genreService.saveGenre(genre),
                    Constants.MESSAGE
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Failed to save.");
        }
    }

    @ApiOperation(value = "Servicio para actualizar un usuario")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateGenre (@PathVariable UUID id, @RequestBody GenreDto genre){

        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    this.genreService.updateGenre(id, genre),
                    Constants.MESSAGE
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Failed to save.");
        }
    }

    @ApiOperation(value = "Servicio para eliminar logicamente un usuario")
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteGenre (@PathVariable UUID id){

        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    Constants.MESSAGE,
                    this.genreService.deleteGenre(id)
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Failed to save.");
        }
    }

}
