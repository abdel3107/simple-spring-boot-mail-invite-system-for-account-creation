package com.example.mailing.controller;

import com.example.mailing.entity.UserEntity;
import com.example.mailing.service.EmailService;
import com.example.mailing.service.JwtUtil;
import com.example.mailing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/invite")
    public ResponseEntity<String> inviteUser(@RequestParam String email) {
        try {
            String token = jwtUtil.generateToken(email);
            String link = "http://localhost:8080/api/v1/users/verify?token=" + token;
            emailService.sendEmail(email, "Invite", "Click the link to register: " + link);

            UserEntity user = new UserEntity();
            user.setEmail(email);
            user.setVerified(false);
            userService.saveUser(user);

            return ResponseEntity.ok("Invitation sent");
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String token) {

        String email = jwtUtil.extractClaims(token).getSubject();
        UserEntity user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(401).body("Invalid token: User not found");
        }

        String expectedEmail = user.getEmail();

        if (jwtUtil.validateToken(token, expectedEmail)) {
            return ResponseEntity.status(400).body("Token expired");
        }

        userService.updateUserVerification(email, true);

        return ResponseEntity.ok("User verified");
    }


}
