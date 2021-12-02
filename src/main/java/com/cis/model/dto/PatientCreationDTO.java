package com.cis.model.dto;

import com.cis.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientCreationDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private String name;
  private String email;
  private String rg;
  private String cpf;
  private Date dateOfBirth;
  private String phone;
  private String motherName;
  private String password;
  private Character gender;
  //  private Address address;

  public PatientCreationDTO(Patient patient) {
    this.name = patient.getName();
    this.email = patient.getEmail();
    this.rg = patient.getRg();
    this.cpf = patient.getCpf();
    this.dateOfBirth = patient.getDateOfBirth();
    this.phone = patient.getPhone();
    this.motherName = patient.getMotherName();
    this.gender = patient.getGender();
    this.password = patient.getPassword();
    //    this.address = patient.getAddress();
  }
}
