package com.grpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@ToString
public class User {
    @Id
    @Column(name = "login", nullable = false)
    private String login;

    private String name;
    private String genre;

}
