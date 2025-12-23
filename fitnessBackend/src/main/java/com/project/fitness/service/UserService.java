package com.project.fitness.service;

import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.model.AppUser;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse register(RegisterRequest registerRequest) {
        AppUser user = AppUser.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .build();
        /*
        AppUser user = new AppUser(
                null,
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                Instant.parse("2025-12-08T14:49:41.208Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
                Instant.parse("2025-12-08T14:49:41.208Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
                List.of(),List.of()
        );
        */

        AppUser savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    private UserResponse mapToResponse(AppUser savedUser) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());
        return userResponse;
    }

}
