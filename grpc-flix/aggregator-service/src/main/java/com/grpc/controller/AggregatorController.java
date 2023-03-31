package com.grpc.controller;

import com.grpc.dto.RecommendedMovie;
import com.grpc.dto.UserGenre;
import com.grpc.service.UserMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AggregatorController {

    @Autowired
    private UserMovieService userMovieService;

    @GetMapping("/user/{loginId}")
    public List<RecommendedMovie> getMovies(@PathVariable String loginId){
        return userMovieService.getUserRecommendedMovies(loginId);
    }

    @PutMapping("/user")
    public void setUserGenre (@RequestBody UserGenre userGenre){
        userMovieService.setUserGenre(userGenre);
    }
}
