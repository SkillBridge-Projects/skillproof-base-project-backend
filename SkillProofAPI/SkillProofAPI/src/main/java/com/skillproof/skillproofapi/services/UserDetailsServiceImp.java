package com.skillproof.skillproofapi.services;

import com.skillproof.skillproofapi.repositories.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class UserDetailsServiceImp implements UserDetailsService {

    private UserDao applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        com.skillproof.skillproofapi.model.entity.User applicationUser = applicationUserRepository.findUserByUserName(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(applicationUser.getRole().getName().name()));

        return new User(applicationUser.getUserName(), applicationUser.getPassword(), grantedAuthorities);
    }
}
