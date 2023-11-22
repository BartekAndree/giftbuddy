package com.giftbuddygroup.giftbuddy.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giftbuddygroup.giftbuddy.common.exception.EventNotFoundException;
import com.giftbuddygroup.giftbuddy.common.exception.UserProfileNotFoundException;
import com.giftbuddygroup.giftbuddy.model.dto.request.EventRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.EventResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.Event;
import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;
import com.giftbuddygroup.giftbuddy.model.mapper.EventMapper;
import com.giftbuddygroup.giftbuddy.repository.EventRepository;
import com.giftbuddygroup.giftbuddy.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {

    private EventRepository eventRepository;
    private UserProfileRepository userProfileRepository;

    @Autowired
    public EventService(
            EventRepository eventRepository, UserProfileRepository userProfileRepository) {
        this.eventRepository = eventRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public EventResponseDTO getEventById(UUID id) {
        Event event = findEventOrThrow(id);
        return EventMapper.toEventResponseDTO(event);
    }

    public List<EventResponseDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return EventMapper.toEventResponseDTOs(events);
    }

    public void deleteEvent(UUID id) {
        Event event = findEventOrThrow(id);
        eventRepository.deleteById(event.getId());
        EventResponseDTO response = EventMapper.toEventResponseDTO(event);
        log.info("Event deleted: {}", response);
    }

    public EventResponseDTO createEvent(EventRequestDTO eventRequestDTO) {
        UserProfile organizer = findUserProfileOrThrow(eventRequestDTO.getOrganizerId());
        Event event = EventMapper.toEvent(eventRequestDTO);
        event.setOrganizer(organizer);
        event.setIsActive(true);
        event = eventRepository.save(event);
        EventResponseDTO response = EventMapper.toEventResponseDTO(event);
        log.info("Event created: {}", response);
        return response;
    }

    public EventResponseDTO updateEvent(UUID eventId, EventRequestDTO eventRequestDTO) {
        Event event = findEventOrThrow(eventId);
        updateEventData(event, eventRequestDTO);
        event = eventRepository.save(event);
        EventResponseDTO response = EventMapper.toEventResponseDTO(event);
        log.info("Event updated: {}", response);
        return response;
    }

    private void updateEventData(Event event, EventRequestDTO eventRequestDTO) {

        if (eventRequestDTO.getTitle() != null) {
            event.setTitle(eventRequestDTO.getTitle());
        }
        if (eventRequestDTO.getDescription() != null) {
            event.setDescription(eventRequestDTO.getDescription());
        }
        if (eventRequestDTO.getGiftIdea() != null) {
            event.setGiftIdea(eventRequestDTO.getGiftIdea());
        }
        if (eventRequestDTO.getImageUrl() != null) {
            event.setImageUrl(eventRequestDTO.getImageUrl());
        }
        if (eventRequestDTO.getContribution() != null) {
            event.setContribution(eventRequestDTO.getContribution());
        }
        if (eventRequestDTO.getTargetAmount() != null) {
            event.setTargetAmount(eventRequestDTO.getTargetAmount());
        }
        if (eventRequestDTO.getEventDate() != null) {
            event.setEventDate(eventRequestDTO.getEventDate());
        }
        if (eventRequestDTO.getEndDate() != null) {
            event.setEndDate(eventRequestDTO.getEndDate());
        }
    }

    private Event findEventOrThrow(UUID id) {
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }

    private UserProfile findUserProfileOrThrow(UUID id) {
        return userProfileRepository
                .findById(id)
                .orElseThrow(() -> new UserProfileNotFoundException(id));
    }
}
