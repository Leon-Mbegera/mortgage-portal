package com.mortgageportal.dto.event;

import com.mortgageportal.entity.ApplicationStatus;
import java.time.OffsetDateTime;

public record ApplicationEvent(
  String eventType,
  Long applicationId,
  Long applicantId,
  ApplicationStatus status,
  OffsetDateTime occuredAt
) {}
