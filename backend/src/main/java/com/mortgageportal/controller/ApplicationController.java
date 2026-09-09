package com.mortgageportal.controller;

import com.mortgageportal.dto.application.ApplicationRequest;
import com.mortgageportal.dto.application.UpdateStatusRequest;
import com.mortgageportal.dto.application.ApplicationResponse;
import com.mortgageportal.security.UserPrincipal;
import com.mortgageportal.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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

  @GetMapping
  public ResponseEntity<List<ApplicationResponse>> list(@AuthenticationPrincipal UserPrincipal principal) {
    return ResponseEntity.ok(applicationService.listForCurrentUser(principal.getUser()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApplicationResponse> getById(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
    return ResponseEntity.ok(applicationService.getById(id, principal.getUser()));
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<ApplicationResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatusRequest request) {
    return ResponseEntity.ok(applicationService.updateStatus(id, request));
  }
}
