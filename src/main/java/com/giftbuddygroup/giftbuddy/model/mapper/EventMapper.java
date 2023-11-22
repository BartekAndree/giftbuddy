package com.giftbuddygroup.giftbuddy.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.giftbuddygroup.giftbuddy.model.dto.request.EventRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.EventResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.Event;

public class EventMapper {

    public static EventResponseDTO toEventResponseDTO(Event event) {
        return EventResponseDTO.builder()
                .id(event.getId())
                .organizerId(event.getOrganizer().getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .giftIdea(event.getGiftIdea())
                .imageUrl(event.getImageUrl())
                .contribution(event.getContribution())
                .currentAmount(event.getCurrentAmount())
                .targetAmount(event.getTargetAmount())
                .eventDate(event.getEventDate())
                .endDate(event.getEndDate())
                .isActive(event.getIsActive())
                .build();
    }

    // Remember to set Organizer after calling this method
    public static Event toEvent(EventRequestDTO eventRequestDTO) {
        return Event.builder()
                .title(eventRequestDTO.getTitle())
                .description(eventRequestDTO.getDescription())
                .giftIdea(eventRequestDTO.getGiftIdea())
                .imageUrl(eventRequestDTO.getImageUrl())
                .contribution(eventRequestDTO.getContribution())
                .targetAmount(eventRequestDTO.getTargetAmount())
                .eventDate(eventRequestDTO.getEventDate())
                .endDate(eventRequestDTO.getEndDate())
                .build();
    }

    public static List<EventResponseDTO> toEventResponseDTOs(List<Event> events) {
        return events.stream().map(EventMapper::toEventResponseDTO).collect(Collectors.toList());
    }
}
