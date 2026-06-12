package com.mortgageportal.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 50)
  private String lastName;

  @Column(nullable = false, unique =  true, length = 255)
  private String email;

  @Column(nullable = false, length = 255)
  private String password;

  @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

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
