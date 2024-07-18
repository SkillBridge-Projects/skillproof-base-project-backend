//package com.skillproof.skillproofapi.security;
//
//
//import com.skillproof.skillproofapi.model.entity.User;
//import com.skillproof.skillproofapi.services.user.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//@Component
//public class GuardUser {
//
//    @Autowired
//    UserService userService;
//
//    public boolean checkUserId(Authentication authentication, int id) {
//        String name = authentication.getName();
//        User result = userService.findUserByEmailAddress(name);
//        return result != null;
//    }
//}
