package com.example.tutorai.config;

import ch.qos.logback.core.util.StringUtil;
import com.example.tutorai.User.Domain.User;
import io.micrometer.core.instrument.binder.http.HttpServletRequestTagsProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserFinder userFinder;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,FilterChain chain)
        throws ServletException, IOException{
        String auth=req.getHeader("Authorization");
        if(!StringUtils.hasText(auth) || auth.startsWith("Bearer ")){
            chain.doFilter(req,res);
            return;
        }

        String token=auth.substring(7);
        try{
            jwtService.validate(token);
            String subject=jwtService.extractSubject(token);
            Long userId=Long.valueOf(subject);

            User user=userFinder.findByIdOrThrow(userId);

            var authToken=new UsernamePasswordAuthenticationToken(
                    user,token,user.getRole().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }catch(Exception ignored){
        }
        chain.doFilter(req,res);
    }
}
