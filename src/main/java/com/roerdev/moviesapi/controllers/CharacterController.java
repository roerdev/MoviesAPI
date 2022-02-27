package com.roerdev.moviesapi.controllers;

import com.roerdev.moviesapi.dto.CharacterDetailDto;
import com.roerdev.moviesapi.dto.ResponseDTO;
import com.roerdev.moviesapi.service.CharacterService;
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
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    @ApiOperation(value = "Servicio para obtener un personaje por ID")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getCharacter (@PathVariable UUID id){

        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    this.characterService.getCharacter(id),
                    Constants.MESSAGE
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Not founds.");
        }
    }

    @ApiOperation(value = "Servicio para obtener todos los pesonajes registrados")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAllCharacter(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "age", required = false) Integer age,
            @RequestParam(name = "movie", required = false) UUID movie,
            Pageable paginador
    ) {
        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    this.characterService.getAllCharacters(name, age, movie, paginador),
                    Constants.MESSAGE
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("");
        }
    }

    @ApiOperation(value = "Servicio para guardar un nuevo personaje")
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> saveCharacter(@RequestBody CharacterDetailDto character){

        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    this.characterService.saveCharacter(character),
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
    public ResponseEntity<?> updateCharacter (@PathVariable UUID id, @RequestBody CharacterDetailDto character){

        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    this.characterService.updateCharacter(id, character),
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
    public ResponseEntity<?> deleteCharacter (@PathVariable UUID id){

        try {
            ResponseDTO response = new ResponseDTO(
                    HttpStatus.OK.getReasonPhrase(),
                    Constants.MESSAGE,
                    this.characterService.deleteCharacter(id)
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException("Failed to save.");
        }
    }
}
