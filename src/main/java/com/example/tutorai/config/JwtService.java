package com.example.tutorai.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.tutorai.User.Domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("secret")
    private String secret;

    public String generateToken(Long userId, Role role){
        Date now=new Date();
        Date exp=new Date(now.getTime()+ 1000L*60*60*8);

        return JWT.create().
                withSubject(String.valueOf(userId)).
                withClaim("role",role.name()).
                withIssuedAt(now).
                withExpiresAt(exp).
                sign(Algorithm.HMAC256(secret));
    }

    public String extractSubject(String token){
        return JWT.decode(token).getSubject();
    }

    public void validate(String token){
        JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }

}
