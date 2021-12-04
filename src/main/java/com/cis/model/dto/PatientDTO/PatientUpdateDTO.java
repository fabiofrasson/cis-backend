package com.cis.model.dto.PatientDTO;

import com.cis.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientUpdateDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private UUID id;
  private String name;
  private String email;
  private String phone;
  private Character gender;
  //  private Address address;

  public PatientUpdateDTO(Patient patient) {
    this.id = patient.getId();
    this.name = patient.getName();
    this.email = patient.getEmail();
    this.phone = patient.getPhone();
    this.gender = patient.getGender();
    //    this.address = patient.getAddress();
  }
}
