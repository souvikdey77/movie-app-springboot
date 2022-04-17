package com.techstudy.moviebookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

/**
 * DTO class of popular movie response
 * @author souvikdey
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PopularMovieResponse {

    private int page;
    private List<MovieDescription> results;
}
