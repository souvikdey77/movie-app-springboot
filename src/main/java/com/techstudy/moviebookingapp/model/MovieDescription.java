package com.techstudy.moviebookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO class of movie description
 * @author souvikdey
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDescription {

    private String adult;
    private String id;
    private String overview;
    private float popularity;
    private String poster_path;
    private String backdrop_path;
    private String release_date;
    private String title;
    private float vote_average;

}
