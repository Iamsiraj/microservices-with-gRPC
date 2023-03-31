package com.grpc.service;

import com.grpc.entity.User;
import com.grpc.repository.UserRepository;
import com.siraj.grpcflix.common.Genre;
import com.siraj.grpcflix.user.UserGenreUpdateRequest;
import com.siraj.grpcflix.user.UserResponse;
import com.siraj.grpcflix.user.UserSearchRequest;
import com.siraj.grpcflix.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        Optional<User> user = userRepository.findById(request.getLoginId());
        if(user.isPresent()){
            User user1 = user.get();
            UserResponse userResponse = UserResponse.newBuilder().setGenre(Genre.valueOf(user1.getGenre().toUpperCase())).setName(user1.getName()).setLoginId(user1.getLogin()).build();
            responseObserver.onNext(userResponse);
            responseObserver.onCompleted();
        }
    }

    @Override
    @Transactional
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        userRepository.findById(request.getLoginId())
               .ifPresent(user->{
                   user.setGenre(request.getGenre().toString());
                   builder.setName(user.getName()).setLoginId(user.getLogin()).setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
               });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
