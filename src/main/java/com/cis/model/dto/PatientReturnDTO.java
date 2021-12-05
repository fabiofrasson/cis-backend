package com.cis.model.dto;

import com.cis.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientReturnDTO {

  private UUID id;

  private String name;
  private String email;
  private UUID addressId;

  public PatientReturnDTO(Patient patient) {
    this.id = patient.getId();
    this.name = patient.getName();
    this.email = patient.getEmail();
    this.addressId = patient.getAddress().getId();
  }
}
