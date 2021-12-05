package com.cis.repository;

import com.cis.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

  // CHANGE TO OPTIONAL<PATIENT> ?
  Patient findByEmailIgnoreCase(String email);
}
