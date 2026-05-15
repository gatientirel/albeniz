package com.theodo.albeniz.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.theodo.albeniz.repositories.UserEntityRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepository.findByUsername(username);
    }
}
