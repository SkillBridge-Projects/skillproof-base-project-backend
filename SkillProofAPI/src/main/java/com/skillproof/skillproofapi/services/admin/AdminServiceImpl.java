package com.skillproof.skillproofapi.services.admin;

import com.skillproof.skillproofapi.model.entity.Picture;
import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.repositories.UserDao;
import com.skillproof.skillproofapi.utils.PictureSave;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserDao userDao;

    @Override
    public List<User> listUsers() {
        List<User> users = userDao.findAll();
        for (User u : users) {
            Picture uPic = u.getProfilePicture();
            if (uPic != null && uPic.isCompressed()) {
                Picture temp = new Picture(uPic.getName(), uPic.getType(), PictureSave.decompressBytes(uPic.getBytes()));
                temp.setCompressed(false);
                u.setProfilePicture(temp);
            }
        }
        return users;
    }
}
