package com.giftbuddygroup.giftbuddy.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giftbuddygroup.giftbuddy.model.entity.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    Optional<Participant> findByEventIdAndUserId(UUID eventId, UUID userId);

    Boolean existsByEventIdAndUserId(UUID eventId, UUID userId);

    List<Participant> findByEventId(UUID eventId);
}
