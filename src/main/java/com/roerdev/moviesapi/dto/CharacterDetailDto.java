package com.roerdev.moviesapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterDetailDto {

    private UUID id;
    private String image;
    private String name;
    private Integer age;
    private Double weight;
    private String history;
    private List<MovieDto> moviesDto;
}
