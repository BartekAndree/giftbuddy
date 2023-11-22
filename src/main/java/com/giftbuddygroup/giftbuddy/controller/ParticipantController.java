package com.giftbuddygroup.giftbuddy.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.giftbuddygroup.giftbuddy.model.dto.request.ParticipantRequestDTO;
import com.giftbuddygroup.giftbuddy.model.dto.response.ParticipantResponseDTO;
import com.giftbuddygroup.giftbuddy.service.ParticipantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/participants")
public class ParticipantController {

    private ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping
    public ResponseEntity<ParticipantResponseDTO> createEvent(
            @RequestBody @Valid ParticipantRequestDTO participantRequestDTO) {
        ParticipantResponseDTO participantResponseDTO =
                participantService.createParticipant(participantRequestDTO);
        return new ResponseEntity<>(participantResponseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable UUID participantId) {
        participantService.deleteParticipant(participantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<List<ParticipantResponseDTO>> getParticipantsByEventId(
            @PathVariable UUID eventId) {
        List<ParticipantResponseDTO> participantsResponseDTOs =
                participantService.getParticipantsByEventId(eventId);
        return new ResponseEntity<>(participantsResponseDTOs, HttpStatus.OK);
    }
}
