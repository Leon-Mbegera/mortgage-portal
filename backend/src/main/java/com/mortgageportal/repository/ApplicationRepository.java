package com.mortgageportal.repository;

import com.mortgageportal.entity.Application;
import com.mortgageportal.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
  List<Application> findByApplicantId(Long applicant_id);
  List<Application> findByStatus(ApplicationStatus status);
}
