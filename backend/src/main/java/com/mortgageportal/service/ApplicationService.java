package com.mortgageportal.service;

import com.mortgageportal.dto.application.ApplicationRequest;
import com.mortgageportal.dto.application.UpdateStatusRequest;
import com.mortgageportal.dto.application.ApplicationResponse;
import com.mortgageportal.dto.event.ApplicationEvent;
import com.mortgageportal.event.ApplicationEventPublisher;
import com.mortgageportal.repository.ApplicationRepository;
import com.mortgageportal.entity.Application;
import com.mortgageportal.entity.ApplicationStatus;
import com.mortgageportal.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.time.OffsetDateTime;

@Service
public class ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final ApplicationEventPublisher eventPublisher;

  public ApplicationService(ApplicationRepository applicationRepository, ApplicationEventPublisher eventPublisher) {
    this.applicationRepository = applicationRepository;
    this.eventPublisher = eventPublisher;
  }

  @Transactional
  public ApplicationResponse submit(ApplicationRequest request, User applicant) {
    Application application = Application.builder()
      .applicant(applicant)
      .loanAmount(request.loanAmount())
      .propertyAddress(request.propertyAddress())
      .status(ApplicationStatus.PENDING)
      .build();

    applicationRepository.save(application);

    eventPublisher.publish(new ApplicationEvent(
      "CREATED",
      application.getId(),
      applicant.getId(),
      application.getStatus(),
      OffsetDateTime.now()
    ));

    return toResponse(application);
  }

  @Transactional(readOnly = true)
  public List<ApplicationResponse> listForCurrentUser(User currentUser) {
    List<Application> applications = currentUser.getRole().getName().equals("ADMIN")
                  ? applicationRepository.findAll()
                  : applicationRepository.findByApplicantId(currentUser.getId());

    return applications.stream().map(this::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public ApplicationResponse getById(Long id, User currentUser) {
    Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

    boolean isOwner = application.getApplicant().getId().equals(currentUser.getId());
    boolean isAdmin = currentUser.getRole().getName().equals("ADMIN");

    if (!isOwner && !isAdmin) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this application");
    }

    return toResponse(application);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @Transactional
  public ApplicationResponse updateStatus(Long id, UpdateStatusRequest request) {
    Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

    if (request.status() == ApplicationStatus.PENDING) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status must be APPROVED or REJECTED");
    }

    application.setStatus(request.status());
    return toResponse(application);
  }


  private ApplicationResponse toResponse(Application application) {
    return new ApplicationResponse(
      application.getId(),
      application.getApplicant().getId(),
      application.getApplicant().getFirstName() + " " + application.getApplicant().getLastName(),
      application.getLoanAmount(),
      application.getPropertyAddress(),
      application.getStatus(),
      application.getCreatedAt(),
      application.getUpdatedAt()
    );
  }
}
