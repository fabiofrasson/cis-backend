package com.cis.repository;

import com.cis.model.Patient;
import com.cis.model.dto.PatientReturnDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

  Patient findByEmailIgnoreCase(String email);
}
