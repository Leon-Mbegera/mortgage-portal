package com.mortgageportal.service;

import com.mortgageportal.dto.application.ApplicationRequest;
import com.mortgageportal.dto.application.ApplicationResponse;
import com.mortgageportal.repository.ApplicationRepository;
import com.mortgageportal.entity.Application;
import com.mortgageportal.entity.ApplicationStatus;
import com.mortgageportal.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationService {

  public final ApplicationRepository applicationRepository;

  public ApplicationService(ApplicationRepository applicationRepository) {
    this.applicationRepository = applicationRepository;
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
