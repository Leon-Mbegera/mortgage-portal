package com.mortgageportal.dto.auth;

public record AuthResponse(
  String token,
  String tokenType,
  Long userId,
  String firstName,
  String lastName,
  String email,
  String role
) {}
