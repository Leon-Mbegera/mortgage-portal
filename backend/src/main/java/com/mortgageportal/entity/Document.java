package com.mortgageportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Document {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

  @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

  @Column(name = "file_path", nullable = false, length = 255)
    private String filePath;

  @Column(name = "content_type", nullable = false, length = 100)
    private String contentType;

  @Column(name = "uploaded_at", nullable = false, updatable = false)
    private OffsetDateTime uploadedAt;

  @PrePersist
    protected void uploadedAt() {
      uploadedAt = OffsetDateTime.now();
    }
}
