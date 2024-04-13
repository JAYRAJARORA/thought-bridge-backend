package com.example.healthapp.service;

import com.example.healthapp.exception.DuplicateUsernameException;
import com.example.healthapp.model.LoginResponse;
import com.example.healthapp.model.Therapist;
import com.example.healthapp.model.User;
import com.example.healthapp.repository.TherapistRepository;
import com.example.healthapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private TherapistRepository therapistRepository;
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        user.setRole("user");
        return userRepository.save(user);
    }

    public Therapist createTherapist(Therapist therapist) {
        therapist.setRole("therapist");
        return therapistRepository.save(therapist);
    }

    public LoginResponse findUser(User user, String role) {
        LoginResponse response = new LoginResponse();
        User foundUser = userRepository.findByUsernameAndPasswordAndRole(
                user.getUsername(), user.getPassword(), role
        );

        if(foundUser != null) {
            response.setUsername(foundUser.getUsername());
            response.setRole(role);
            response.setUserId(foundUser.getId());
        }

        return response;
    }


    public ResponseEntity<?> createUserOrTherapist(String role, Object userData) {
        try {
            Object createdEntity = null;
            if(role.equals("user") && userData instanceof User user) {
                createdEntity = this.createUser(user);
            } else if(role.equals("therapist") && userData instanceof Therapist therapist) {
                createdEntity = this.createTherapist(therapist);
            }
            if (createdEntity != null) {
                // Save successful
                return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
            } else {
                // Save failed
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to save user");
            }
        } catch(DuplicateUsernameException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch(Exception e) {
            System.out.println("Error in saving user" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save user");
        }
    }


}
