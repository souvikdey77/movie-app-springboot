package com.techstudy.moviebookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetails {
    private String adult;
    private Long budget;
    private Long id;
    private String overview;
    private float popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private float vote_average;
    private int vote_count;
}
