package com.project.fitness.service;

import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.model.AppUser;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<AppUser> getUserById(String id) {
        return ResponseEntity.ok(userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User with id " + id + " not found")
        ));
    }

    public UserResponse createUser(RegisterRequest user) {
        return userService.register(user);
    }

    public UserResponse updateUser(AppUser user) {

        AppUser updatedUser = userRepository.findById(user.getId()).orElseThrow(()->new RuntimeException("User Not Found"));
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setRole(user.getRole());
        if(user.getPassword()!=null && !user.getPassword().isBlank()){
            updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        AppUser savedUser = userRepository.save(updatedUser);
        return userService.mapToResponse(savedUser);
    }

    public UserResponse deleteUser(String id) {
        AppUser user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User with id " + id + " not found"));
        UserResponse response = userService.mapToResponse(user);
        userRepository.delete(user);
        return response;
    }
}
