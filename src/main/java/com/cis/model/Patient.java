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
public class Patient extends User implements Serializable {
  private static final long serialVersionUID = 1L;

  @Column(nullable = false)
  private UUID patientId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String rg;

  @Column(nullable = false)
  private String cpf;

  @Column(nullable = false)
  private Date dateOfBirth;

  @Column(nullable = false)
  private String phone;

  @Column(nullable = false)
  private String motherName;

  @Column(nullable = false)
  private Character gender;

  @Column(nullable = false)
  private String addressNumber;

  @Column(nullable = false)
  // Complemento do endereço
  private String addressLine2;

  @JoinColumn(name = "addressId")
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Address address;
}
