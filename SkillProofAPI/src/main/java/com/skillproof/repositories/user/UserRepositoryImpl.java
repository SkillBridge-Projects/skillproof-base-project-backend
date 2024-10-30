package com.skillproof.repositories.user;

import com.skillproof.enums.RoleType;
import com.skillproof.model.entity.User;
import com.skillproof.repositories.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final UserDao userDao;

    public UserRepositoryImpl(UserDao userDao){
        this.userDao = userDao;
    }


    @Override
    public User createUser(User user) {
        LOG.info("Start of createUser method.");
        return userDao.saveAndFlush(user);
    }

    @Override
    public User getUserById(String id) {
        LOG.info("Start of createUser method.");
        return userDao.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String userName) {
        Optional<User> user = userDao.findByEmailAddressIgnoreCase(userName);
        return user.orElse(null);
    }

    @Override
    public List<User> listAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void deleteUserById(String id) {
        userDao.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        return userDao.saveAndFlush(user);
    }

    @Override
    public List<User> listUsersByRole(RoleType roleType) {
        return userDao.findAllByRole(roleType);
    }
}
