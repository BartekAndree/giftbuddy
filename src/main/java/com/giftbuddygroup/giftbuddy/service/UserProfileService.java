package com.giftbuddygroup.giftbuddy.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giftbuddygroup.giftbuddy.common.exception.EmailAlreadyExistsException;
import com.giftbuddygroup.giftbuddy.common.exception.UsernameAlreadyExistsException;
import com.giftbuddygroup.giftbuddy.model.dto.request.UserProfileRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.UserProfileResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;
import com.giftbuddygroup.giftbuddy.model.mapper.UserProfileMapper;
import com.giftbuddygroup.giftbuddy.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileService {

    private UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfileResponseDTO createUser(UserProfileRequestDTO userProfileRequestDTO) {
        checkUsernameAvailability(userProfileRequestDTO.getUsername());
        checkEmailAvailability(userProfileRequestDTO.getEmail());
        UserProfile userProfile = UserProfileMapper.toUserProfile(userProfileRequestDTO);
        userProfile = userProfileRepository.save(userProfile);
        UserProfileResponseDTO response = UserProfileMapper.toUserProfileResponseDTO(userProfile);
        log.info("User created: {}", response);
        return response;
    }

    public List<UserProfileResponseDTO> getAllUsers() {
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        return UserProfileMapper.toUserProfileResponseDTOs(userProfiles);
    }

    public UserProfileResponseDTO getUserById(UUID id) {
        UserProfile userProfile = findUserOrThrow(id);
        return UserProfileMapper.toUserProfileResponseDTO(userProfile);
    }

    public void deleteUser(UUID id) {
        UserProfile userProfile = findUserOrThrow(id);
        userProfileRepository.deleteById(userProfile.getId());
        UserProfileResponseDTO response = UserProfileMapper.toUserProfileResponseDTO(userProfile);
        log.info("User deleted: {}", response);
    }

    public UserProfileResponseDTO updateUser(UUID id, UserProfileRequestDTO userProfileRequestDTO) {
        UserProfile existingUserProfile = findUserOrThrow(id);

        validateUsernameIfChanged(existingUserProfile, userProfileRequestDTO);
        validateEmailIfChanged(existingUserProfile, userProfileRequestDTO);

        UserProfile updatedUserProfile =
                applyUpdatesToUserProfile(existingUserProfile, userProfileRequestDTO);

        updatedUserProfile = userProfileRepository.save(updatedUserProfile);

        UserProfileResponseDTO response =
                UserProfileMapper.toUserProfileResponseDTO(updatedUserProfile);
        log.info("User updated: {}", response);
        return response;
    }

    private UserProfile applyUpdatesToUserProfile(
            UserProfile existingUserProfile, UserProfileRequestDTO userProfileRequestDTO) {
        UserProfile updatedUserProfile = existingUserProfile.copy();
        updatedUserProfile.setUsername(userProfileRequestDTO.getUsername());
        updatedUserProfile.setEmail(userProfileRequestDTO.getEmail());
        updatedUserProfile.setFirstName(userProfileRequestDTO.getFirstName());
        updatedUserProfile.setLastName(userProfileRequestDTO.getLastName());
        updatedUserProfile.setPhoneNumber(userProfileRequestDTO.getPhoneNumber());
        return updatedUserProfile;
    }

    private UserProfile findUserOrThrow(UUID id) {
        return userProfileRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(id.toString()));
    }

    private void checkUsernameAvailability(String username) {
        userProfileRepository
                .findByUsername(username)
                .ifPresent(
                        user -> {
                            throw new UsernameAlreadyExistsException(username);
                        });
    }

    private void checkEmailAvailability(String email) {
        userProfileRepository
                .findByEmail(email)
                .ifPresent(
                        user -> {
                            throw new EmailAlreadyExistsException(email);
                        });
    }

    private void validateUsernameIfChanged(
            UserProfile existingUserProfile, UserProfileRequestDTO userProfileRequestDTO) {
        if (!existingUserProfile.getUsername().equals(userProfileRequestDTO.getUsername())) {
            checkUsernameAvailability(userProfileRequestDTO.getUsername());
        }
    }

    private void validateEmailIfChanged(
            UserProfile existingUserProfile, UserProfileRequestDTO userProfileRequestDTO) {
        if (!existingUserProfile.getEmail().equals(userProfileRequestDTO.getEmail())) {
            checkEmailAvailability(userProfileRequestDTO.getEmail());
        }
    }
}
