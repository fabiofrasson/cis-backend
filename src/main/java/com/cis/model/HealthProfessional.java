package com.cis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthProfessional extends User implements Serializable {
  private static final long serialVersionUID = 1L;

  @Column(updatable = false, nullable = false)
  private UUID professional_id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String phone;

  @Column(nullable = false)
  private String professionalDocument;

  @OneToMany(mappedBy = "professional")
  private List<Schedule> schedules;
}
