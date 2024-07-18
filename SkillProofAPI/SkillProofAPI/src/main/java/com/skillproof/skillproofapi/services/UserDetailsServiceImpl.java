package com.skillproof.skillproofapi.services;

import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.repositories.UserDao;
import com.skillproof.skillproofapi.repositories.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User applicationUser = userRepository.getUserByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(applicationUser.getRole().name()));

        return new org.springframework.security.core.userdetails.User(applicationUser.getEmailAddress(),
                applicationUser.getPassword(), grantedAuthorities);
    }
}
