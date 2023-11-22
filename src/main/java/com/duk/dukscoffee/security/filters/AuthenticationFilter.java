package com.duk.dukscoffee.security.filters;

import com.duk.dukscoffee.entities.UserEntity;
import com.duk.dukscoffee.http.DTO.UserDTO;
import com.duk.dukscoffee.respositories.UserEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserEntityRepository userEntityRepository;

    public AuthenticationFilter(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // create a user and mapped the values from request. It's going be email and password
        UserEntity user = null;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserDTO user = new ModelMapper()
                .map(userEntityRepository.findUserByEmail(((User) authResult.getPrincipal()).getUsername()).get(), UserDTO.class);


        Map<String, Object> httpResponse = new HashMap<>();

        httpResponse.put("message", "Successful authentication done!");
        httpResponse.put("user", user);

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }

}

