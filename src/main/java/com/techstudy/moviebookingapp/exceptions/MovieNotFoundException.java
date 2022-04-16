package com.techstudy.moviebookingapp.exceptions;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(){
        super("No movie is available");
    }
}
