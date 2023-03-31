package com.grpc.service;

import com.grpc.entity.Movie;
import com.grpc.repository.MovieRepository;
import com.siraj.grpcflix.common.Genre;
import com.siraj.grpcflix.movies.MovieDto;
import com.siraj.grpcflix.movies.MovieSearchRequest;
import com.siraj.grpcflix.movies.MovieSearchResponse;
import com.siraj.grpcflix.movies.MovieServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {
    @Autowired
    private MovieRepository movieRepository;
    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {

        List<Movie> movieByGenreOrderByYearDesc = movieRepository.getMovieByGenreOrderByYearDesc(request.getGenre().toString());
        if(movieByGenreOrderByYearDesc!=null && !movieByGenreOrderByYearDesc.isEmpty()){
            MovieSearchResponse.Builder builder = MovieSearchResponse.newBuilder();
            List<MovieDto> movieDtos = new ArrayList<>();
            for (Movie movie: movieByGenreOrderByYearDesc) {
               movieDtos.add(MovieDto.newBuilder().setTitle(movie.getTitle()).setRating(movie.getRating()).setYear(movie.getYear()).build());
            }
            builder.addAllMovie(movieDtos);

            responseObserver.onNext(builder.build());

        }

        responseObserver.onCompleted();



    }
}
