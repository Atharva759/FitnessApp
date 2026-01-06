package com.project.fitness.controller;

import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.model.AppUser;
import com.project.fitness.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminUserService adminUserService;

    // User Endpoints
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<AppUser>> getAllUsers(){
        return adminUserService.getAllUsers();
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<AppUser> getUserById(@RequestParam String id){
        return adminUserService.getUserById(id);
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponse> createUser(@RequestBody RegisterRequest user){
        UserResponse response = adminUserService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PatchMapping("/updateUser")
    public ResponseEntity<UserResponse> updateUser(@RequestBody AppUser user){
        UserResponse response = adminUserService.updateUser(user);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteUser/{id}")
    public UserResponse deleteUser(@RequestParam String id){
        UserResponse response = adminUserService.deleteUser(id);
        return ResponseEntity.ok(response).getBody();
    }

}
