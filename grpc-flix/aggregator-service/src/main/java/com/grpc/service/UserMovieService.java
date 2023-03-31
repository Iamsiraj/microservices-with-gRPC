package com.grpc.service;

import com.grpc.dto.RecommendedMovie;
import com.grpc.dto.UserGenre;
import com.siraj.grpcflix.common.Genre;
import com.siraj.grpcflix.movies.MovieSearchRequest;
import com.siraj.grpcflix.movies.MovieSearchResponse;
import com.siraj.grpcflix.movies.MovieServiceGrpc;
import com.siraj.grpcflix.user.UserGenreUpdateRequest;
import com.siraj.grpcflix.user.UserResponse;
import com.siraj.grpcflix.user.UserSearchRequest;
import com.siraj.grpcflix.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMovieService {
    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieServiceBlockingStub;

    public List<RecommendedMovie> getUserRecommendedMovies(String loginId){
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder().setLoginId(loginId).build();
        UserResponse userResponse = userServiceBlockingStub.getUserGenre(userSearchRequest);


        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder().setGenre(Genre.valueOf(userResponse.getGenre().toString())).build();
        MovieSearchResponse movies = movieServiceBlockingStub.getMovies(movieSearchRequest);
        return movies.getMovieList()
                .stream()
                .map(movieDto -> new RecommendedMovie(movieDto.getTitle(),movieDto.getYear(),movieDto.getRating()))
                .collect(Collectors.toList());

    }

    public void setUserGenre(UserGenre userGenre){

        userServiceBlockingStub.updateUserGenre(UserGenreUpdateRequest.newBuilder().setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase())).setLoginId(userGenre.getLoginId()).build());

    }
}
