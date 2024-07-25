//package com.skillproof.skillproofapi.services.admin;
//
//import com.skillproof.skillproofapi.model.entity.User;
//import com.skillproof.skillproofapi.model.request.user.UserResponse;
//import com.skillproof.skillproofapi.repositories.user.UserRepository;
//import com.skillproof.skillproofapi.utils.ResponseConverter;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AdminServiceImpl implements AdminService {
//
//    private final UserRepository userRepository;
//
//    public AdminServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//
//    @Override
//    public List<UserResponse> listUsers() {
//        List<User> users = userRepository.listAllUsers();
////        for (User u : users) {
////            Picture uPic = u.getProfilePicture();
////            if (uPic != null && uPic.isCompressed()) {
////                Picture temp = new Picture(uPic.getName(), uPic.getType(), PictureSave.decompressBytes(uPic.getBytes()));
////                temp.setCompressed(false);
////                u.setProfilePicture(temp);
////            }
////        }
//        return ResponseConverter.copyListProperties(users, UserResponse.class);
//    }
//}
