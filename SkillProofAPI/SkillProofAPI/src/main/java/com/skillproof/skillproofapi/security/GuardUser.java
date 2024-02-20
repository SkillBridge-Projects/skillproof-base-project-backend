package com.skillproof.skillproofapi.security;


import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class GuardUser {

    @Autowired
    UserDao userDao;

    public boolean checkUserId(Authentication authentication, int id) {
        String name = authentication.getName();
        User result = userDao.findUserByUsername(name);
        return result != null && result.getId() == id;
    }
}
