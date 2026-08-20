package com.mortgageportal.controller;

import com.mortgageportal.dto.application.ApplicationRequest;
import com.mortgageportal.dto.application.ApplicationResponse;
import com.mortgageportal.security.UserPrincipal;
import com.mortgageportal.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")

public class ApplicationController {
  
  private final ApplicationService applicationService;

  public ApplicationController(ApplicationService applicationService) {
    this.applicationService = applicationService;
  }

  @PostMapping
  public ResponseEntity<ApplicationResponse> submit(@Valid @RequestBody ApplicationRequest request, @AuthenticationPrincipal UserPrincipal principal) {
    return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.submit(request, principal.getUser()));
  }
}
