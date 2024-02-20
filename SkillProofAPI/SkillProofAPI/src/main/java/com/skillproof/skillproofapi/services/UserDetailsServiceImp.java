package com.skillproof.skillproofapi.services;

import com.skillproof.skillproofapi.exceptions.EmailNotFoundException;
import com.skillproof.skillproofapi.model.entity.Role;
import com.skillproof.skillproofapi.repositories.UserDao;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private UserDao applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws EmailNotFoundException {
        com.skillproof.skillproofapi.model.entity.User applicationUser = applicationUserRepository.findUserByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : applicationUser.getRoles())
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().name()));
        
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), grantedAuthorities);
    }
}
