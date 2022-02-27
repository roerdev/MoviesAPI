package com.roerdev.moviesapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private UUID id;
    private String image;
    private String title;
    private LocalDateTime createdDate;
}
