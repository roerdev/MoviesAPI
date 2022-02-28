package com.roerdev.moviesapi.controllers;

import com.roerdev.moviesapi.dto.MovieDetailDto;
import com.roerdev.moviesapi.dto.ResponseDTO;
import com.roerdev.moviesapi.service.MovieService;
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
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @ApiOperation(value = "Servicio para obtener una película por ID")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getMovie (@PathVariable UUID id){

        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    this.movieService.getMovie(id),
                    Constants.MESSAGE
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Not founds.");
        }
    }

    @ApiOperation(value = "Servicio para obtener todas las películas registradas")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAllMovie(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "genre", required = false) UUID genre,
            @RequestParam(name = "order", defaultValue = "ASC", required = false) String order,
            Pageable paginador
    ) {
        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    this.movieService.getAllMovies(name, genre, order, paginador),
                    Constants.MESSAGE
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("");
        }
    }

    @ApiOperation(value = "Servicio para guardar una nueva película")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> saveMovie(@RequestBody MovieDetailDto movie){

        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    this.movieService.saveMovie(movie),
                    Constants.MESSAGE
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Failed to save.");
        }
    }

    @ApiOperation(value = "Servicio para actualizar una película")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateMovie (@PathVariable UUID id, @RequestBody MovieDetailDto movie){

        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    this.movieService.updateMovie(id, movie),
                    Constants.MESSAGE
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Failed to save.");
        }
    }

    @ApiOperation(value = "Servicio para eliminar logicamente una película")
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteMovie (@PathVariable UUID id){

        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    Constants.MESSAGE,
                    this.movieService.deleteMovie(id)
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Failed to save.");
        }
    }
}
