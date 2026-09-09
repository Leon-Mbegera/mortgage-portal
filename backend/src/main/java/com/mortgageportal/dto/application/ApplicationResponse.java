package com.mortgageportal.dto.application;

import com.mortgageportal.entity.ApplicationStatus;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ApplicationResponse(
  Long id,
  Long applicantId,
  String applicantName,
  BigDecimal loanAmount,
  String propertyAddress,
  ApplicationStatus status,
  OffsetDateTime createdAt,
  OffsetDateTime updatedAt
) {}
