package com.giftbuddygroup.giftbuddy.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.giftbuddygroup.giftbuddy.model.dto.request.UserProfileRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.UserProfileResponseDTO;
import com.giftbuddygroup.giftbuddy.service.UserProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserProfileController {

    private UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    public ResponseEntity<UserProfileResponseDTO> createUser(
            @RequestBody @Valid UserProfileRequestDTO userProfileRequestDTO) {
        UserProfileResponseDTO userProfileResponseDTO =
                userProfileService.createUser(userProfileRequestDTO);
        return new ResponseEntity<>(userProfileResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserProfileResponseDTO>> getAllUsers() {
        List<UserProfileResponseDTO> userProfileResponseDTOs = userProfileService.getAllUsers();
        return new ResponseEntity<>(userProfileResponseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponseDTO> getUserById(@PathVariable UUID userId) {
        UserProfileResponseDTO userProfileResponseDTO = userProfileService.getUserById(userId);
        return new ResponseEntity<>(userProfileResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserProfileResponseDTO> updateUser(
            @PathVariable UUID userId,
            @RequestBody @Valid UserProfileRequestDTO userProfileRequestDTO) {
        UserProfileResponseDTO userProfileResponseDTO =
                userProfileService.updateUser(userId, userProfileRequestDTO);
        return new ResponseEntity<>(userProfileResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userProfileService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
