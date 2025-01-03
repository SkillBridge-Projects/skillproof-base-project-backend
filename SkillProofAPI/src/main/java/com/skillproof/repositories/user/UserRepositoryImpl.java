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

    public UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUser(User user) {
        LOG.info("Start of createUser method.");
        return userDao.saveAndFlush(user);
    }

    @Override
    public User getUserById(String id) {
        LOG.info("Start of getUserById method.");
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

    @Override
    public Optional<User> getUserByProfilePicture(String profilePicture) {
        return userDao.findByProfilePicture(profilePicture);
    }

    @Override
    public User updateProfilePicture(String userId, String profilePicturePath) {
        LOG.info("Start of updateProfilePicture method for userId: {}", userId);

        Optional<User> optionalUser = userDao.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setProfilePicturePath(profilePicturePath);
            return userDao.saveAndFlush(user);
        } else {
            LOG.warn("User not found with id: {}", userId);
            return null;
        }
    }
}
