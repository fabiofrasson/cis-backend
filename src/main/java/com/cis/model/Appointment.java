package com.cis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointment implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(nullable = false)
  private Date date;

  @Column(nullable = false)
  private Integer hour;

  @Column(nullable = false)
  private Integer minute;

  @ManyToOne
  @JoinColumn(name = "roomId", nullable = false)
  private Room room;

  @ManyToOne
  @JoinColumn(name = "professionalId", nullable = false)
  private HealthProfessional professional;

  @ManyToOne
  @JoinColumn(name = "patientId", nullable = false)
  private Patient patient;

  @Column(nullable = false)
  private String observation;

  @Column(nullable = false)
  private Boolean paid;

  @Column(nullable = false)
  private Double fee;
}
