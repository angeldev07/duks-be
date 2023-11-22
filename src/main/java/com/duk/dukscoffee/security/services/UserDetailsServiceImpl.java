package com.duk.dukscoffee.security.services;

import com.duk.dukscoffee.entities.UserEntity;
import com.duk.dukscoffee.respositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntityRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load user from repository
        UserEntity user = userRepository.findUserByEmail(username).orElseThrow();

        List<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRol()));

        return new User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
