package com.mortgageportal.dto.application;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ApplicationRequest(
  @NotNull @DecimalMin(value = "0.01", message =  "Loan amount must be greater than 0") BigDecimal loanAmount,
  @NotBlank @Size(max = 500) String propertyAddress
) {}
