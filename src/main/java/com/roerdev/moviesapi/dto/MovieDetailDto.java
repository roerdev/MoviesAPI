package com.roerdev.moviesapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.roerdev.moviesapi.models.MovieEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
public class MovieDetailDto {

    private UUID id;
    private String image;
    private String title;
    private Integer ratings;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    private List<CharacterDto> characters = new ArrayList<>();
    private List<GenreDto> genres = new ArrayList<>();

    public MovieDetailDto(MovieEntity entity){
        if(entity != null){
            this.id = entity.getId();
            this.image = entity.getImage();
            this.title = entity.getTitle();
            this.ratings = entity.getRatings();
            this.createdDate = entity.getCreatedDate();
        }
    }
}
