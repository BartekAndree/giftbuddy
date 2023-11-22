package com.giftbuddygroup.giftbuddy.model.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantResponseDTO {
    private UUID id;
    private UUID eventId;
    private UUID userId;
    private BigDecimal amount;
    private Boolean isAnonymous;
}
