package com.giftbuddygroup.giftbuddy.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    Optional<Object> findByUsername(String username);

    Optional<Object> findByEmail(String email);
}
