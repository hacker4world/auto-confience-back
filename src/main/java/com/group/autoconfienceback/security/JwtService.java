package com.group.autoconfienceback.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private String secretKey = "3vAscjdTEAlZHNXdjVprneonzr4CVmmurzgU1cAJT14UMkXjwUM6XJbkyt9Z4l3A39pR3ZoRK2ZBjUgANaV4QstkMNsPiId3b6wQ+FGVifN6VdNOuGbz9sefVb9zoB9NZ7vGJ9y7M8xjcKqVXQQZOhr4QKQbZgIjlBKT3DQs1OofgrLusUbDT1lQqy0XHUxIXe3rJjfBEqtnaqxPLUGhCPqpYc31Q/kRiCrgbn0/0ihVt2yCwYUqjGDyyHsIgiVc/JEitSE29Eeb7WVbuNOmVKP3xOMGqpGi+WpUSmREj7G5wiYHiBb7eGOPYk60BSjTYdK+WdRZ67qkXcIAlGn5ss9hRzBTwfacZ+Wv2WBhWzk=";

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", email);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
