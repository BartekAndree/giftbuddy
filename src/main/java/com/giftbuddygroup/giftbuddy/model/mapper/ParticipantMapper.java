package com.giftbuddygroup.giftbuddy.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.giftbuddygroup.giftbuddy.model.dto.response.ParticipantResponseDTO;
import com.giftbuddygroup.giftbuddy.model.entity.Event;
import com.giftbuddygroup.giftbuddy.model.entity.Participant;
import com.giftbuddygroup.giftbuddy.model.entity.UserProfile;

public class ParticipantMapper {

    public static ParticipantResponseDTO toParticipantResponseDTO(Participant participant) {
        return ParticipantResponseDTO.builder()
                .id(participant.getId())
                .eventId(participant.getEvent().getId())
                .userId(participant.getUser().getId())
                .amount(participant.getAmount())
                .isAnonymous(participant.getIsAnonymous())
                .build();
    }

    public static List<ParticipantResponseDTO> toParticipantResponseDTOs(
            List<Participant> participants) {
        return participants.stream()
                .map(ParticipantMapper::toParticipantResponseDTO)
                .collect(Collectors.toList());
    }

    public static Participant toParticipant(Event event, UserProfile user) {
        return Participant.builder().event(event).user(user).isAnonymous(false).build();
    }
}
