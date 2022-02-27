package com.roerdev.moviesapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "rel_movie_genre")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieGenreEntity {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "movie_fk")
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "genre_fk")
    private GenreEntity genre;
}
