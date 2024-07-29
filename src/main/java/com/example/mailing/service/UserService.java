package com.example.mailing.service;

import com.example.mailing.entity.UserEntity;
import com.example.mailing.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    @Transactional
    public void updateUserVerification(String email, Boolean isVerified) {
        UserEntity user = userRepository.findByEmail(email);
        user.setVerified(isVerified);
    }
}
