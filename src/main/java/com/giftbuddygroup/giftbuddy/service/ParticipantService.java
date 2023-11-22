package com.giftbuddygroup.giftbuddy.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giftbuddygroup.giftbuddy.common.exception.EventNotFoundException;
import com.giftbuddygroup.giftbuddy.common.exception.ParticipantNotFoundException;
import com.giftbuddygroup.giftbuddy.common.exception.UserAlreadyInEventException;
import com.giftbuddygroup.giftbuddy.common.exception.UserProfileNotFoundException;
import com.giftbuddygroup.giftbuddy.model.dto.request.ParticipantRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.ParticipantResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.Event;
import com.giftbuddygroup.giftbuddy.model.entity.Participant;
import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;
import com.giftbuddygroup.giftbuddy.model.mapper.ParticipantMapper;
import com.giftbuddygroup.giftbuddy.repository.EventRepository;
import com.giftbuddygroup.giftbuddy.repository.ParticipantRepository;
import com.giftbuddygroup.giftbuddy.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParticipantService {

    private ParticipantRepository participantRepository;
    private EventRepository eventRepository;
    private UserProfileRepository userProfileRepository;

    @Autowired
    public ParticipantService(
            EventRepository eventRepository,
            UserProfileRepository userProfileRepository,
            ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public ParticipantResponseDTO createParticipant(ParticipantRequestDTO participantRequestDTO) {
        Event event = findEventOrThrow(participantRequestDTO.getEventId());
        UserProfile user = findUserProfileOrThrow(participantRequestDTO.getUserId());
        userShouldNotExistInEvent(event.getId(), user.getId());
        Participant participant = ParticipantMapper.toParticipant(event, user);
        participant = participantRepository.save(participant);
        ParticipantResponseDTO response = ParticipantMapper.toParticipantResponseDTO(participant);
        log.info("Participant created: {}", response);
        return response;
    }

    public void deleteParticipant(UUID participantId) {
        Participant participant = findParticipantOrThrow(participantId);
        participantRepository.delete(participant);
        log.info("Participant deleted: {}", participant);
    }

    public List<ParticipantResponseDTO> getParticipantsByEventId(UUID eventId) {
        Event event = findEventOrThrow(eventId);
        List<Participant> participants = participantRepository.findByEventId(event.getId());
        List<ParticipantResponseDTO> response =
                ParticipantMapper.toParticipantResponseDTOs(participants);
        log.info("Participants found: {}", response);
        return response;
    }

    private Event findEventOrThrow(UUID id) {
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }

    private UserProfile findUserProfileOrThrow(UUID id) {
        return userProfileRepository
                .findById(id)
                .orElseThrow(() -> new UserProfileNotFoundException(id));
    }

    private void userShouldNotExistInEvent(UUID eventId, UUID userId) {
        if (Boolean.TRUE.equals(participantRepository.existsByEventIdAndUserId(eventId, userId))) {
            throw new UserAlreadyInEventException(userId, eventId);
        }
    }

    private Participant findParticipantOrThrow(UUID participantId) {
        return participantRepository
                .findById(participantId)
                .orElseThrow(() -> new ParticipantNotFoundException(participantId));
    }
}
