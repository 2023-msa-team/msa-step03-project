package com.example.userservice.user;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "user_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pk;

    @Column(unique = true)
    private String email;
    private String username;
    private String password;
    private String roles;

    @Builder
    public User(Long id, String pk, String email, String username, String password, String roles) {
        this.id = id;
        this.pk = pk;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }


    public void setUUID(String pk){
        this.pk = pk;
    }

}
