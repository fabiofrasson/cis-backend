package com.cis.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
public class Patient implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  private String name;
  private String email;
  private String rg;
  private String cpf;
  private Date dateOfBirth;
  private String phone;
  private String motherName;
  private Address address;

  public Patient() {}

  public Patient(
      String name,
      String email,
      String rg,
      String cpf,
      Date dateOfBirth,
      String phone,
      String motherName,
      Address address) {
    this.name = name;
    this.email = email;
    this.rg = rg;
    this.cpf = cpf;
    this.dateOfBirth = dateOfBirth;
    this.phone = phone;
    this.motherName = motherName;
    this.address = address;
  }

  public Patient(
      UUID id,
      String name,
      String email,
      String rg,
      String cpf,
      Date dateOfBirth,
      String phone,
      String motherName,
      Address address) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.rg = rg;
    this.cpf = cpf;
    this.dateOfBirth = dateOfBirth;
    this.phone = phone;
    this.motherName = motherName;
    this.address = address;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRg() {
    return rg;
  }

  public void setRg(String rg) {
    this.rg = rg;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getMotherName() {
    return motherName;
  }

  public void setMotherName(String motherName) {
    this.motherName = motherName;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Patient{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append(", rg='").append(rg).append('\'');
    sb.append(", cpf='").append(cpf).append('\'');
    sb.append(", dateOfBirth=").append(dateOfBirth);
    sb.append(", phone='").append(phone).append('\'');
    sb.append(", motherName='").append(motherName).append('\'');
    sb.append(", address=").append(address);
    sb.append('}');
    return sb.toString();
  }
}
