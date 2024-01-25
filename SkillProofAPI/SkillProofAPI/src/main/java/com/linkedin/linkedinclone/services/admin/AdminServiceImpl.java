package com.linkedin.linkedinclone.services.admin;

import com.linkedin.linkedinclone.model.Picture;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.linkedin.linkedinclone.utils.PictureSave.decompressBytes;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    public List<User> listUsers() {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            Picture uPic = u.getProfilePicture();
            if (uPic != null && uPic.isCompressed()) {
                Picture temp = new Picture(uPic.getName(), uPic.getType(), decompressBytes(uPic.getBytes()));
                temp.setCompressed(false);
                u.setProfilePicture(temp);
            }
        }
        return users;
    }
}
