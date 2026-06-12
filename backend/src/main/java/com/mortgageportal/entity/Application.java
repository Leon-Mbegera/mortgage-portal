package com.mortgageportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Application {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

  @Column(name = "loan_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal loanAmount;

  @Column(name = "property_address", length = 500)
    private String propertyAddress;

  @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.PENDING;

  @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Document> documents = new ArrayList<>();

  @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

  @PrePersist
    protected void onCreate() {
      createdAt = OffsetDateTime.now();
      updatedAt = OffsetDateTime.now();
    }

  @PreUpdate
    protected void onUpdate() {
      updatedAt = OffsetDateTime.now();
    }
}
