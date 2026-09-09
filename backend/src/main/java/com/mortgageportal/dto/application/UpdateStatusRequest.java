package com.mortgageportal.dto.application;

import com.mortgageportal.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(
  @NotNull ApplicationStatus status
) {}

