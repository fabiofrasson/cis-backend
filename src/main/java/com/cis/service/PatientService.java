package com.cis.service;

import com.cis.exceptions.BadRequestException;
import com.cis.model.Patient;
import com.cis.model.dto.PatientCreationDTO;
import com.cis.model.dto.PatientReturnDTO;
import com.cis.model.dto.PatientUpdateDTO;
import com.cis.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

  private PatientRepository repository;

  public PatientService(PatientRepository repository) {
    this.repository = repository;
  }

  public Page<Patient> listAll(Pageable pageable) {
    return repository.findAll( pageable);
  }

  public Patient findByIdOrThrowError(UUID id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new BadRequestException("Paciente não encontrado."));
  }

  public Optional<Patient> findByEmailOrThrowError(String email) {

    Optional<Patient> foundPatient = repository.findByEmail(email);

    if (foundPatient.isPresent()) {
      return foundPatient;
    } else {
      throw new BadRequestException("Paciente não encontrado.");
    }
  }

  @Transactional
  public PatientReturnDTO save(PatientCreationDTO patientCreationDTO) {
    Optional<Patient> findPatient = repository.findByEmail(patientCreationDTO.getEmail());
    if (findPatient.isPresent()) {
      throw new BadRequestException("Paciente já cadastrado em nosso sistema.");
    } else {
      Patient patientToBeSaved =
          repository.save(
              Patient.builder()
                  .name(patientCreationDTO.getName())
                  .email(patientCreationDTO.getEmail())
                  .rg(patientCreationDTO.getRg())
                  .cpf(patientCreationDTO.getCpf())
                  .dateOfBirth(patientCreationDTO.getDateOfBirth())
                  .phone(patientCreationDTO.getPhone())
                  .gender(patientCreationDTO.getGender())
                  .password(patientCreationDTO.getPassword())
                  .motherName(patientCreationDTO.getMotherName())
                  //                  .address(patientCreationDTO.getAddress())
                  .build());
      return new PatientReturnDTO(patientToBeSaved);
    }
  }

  public String delete(UUID id) {
    repository.delete(findByIdOrThrowError(id));
    return "Registro deletado com sucesso!";
  }

  public String update(UUID id, PatientUpdateDTO patient) {
    Patient savedPatient = this.findByIdOrThrowError(id);

    //    savedPatient.setName(patient.getName());
    //    savedPatient.setEmail(patient.getEmail());
    //    savedPatient.setPhone(patient.getPhone());
    //    savedPatient.setGender(patient.getGender());

    Patient updatedPatient =
        Patient.builder()
            .id(savedPatient.getId())
            .name(patient.getName())
            .email(patient.getEmail())
            .phone(patient.getPhone())
            .gender(patient.getGender())
            //            .address(patient.getAddress())
            .build();

    repository.save(updatedPatient);
    //    repository.save(savedPatient);
    return "Registro atualizado com sucesso!";
  }
}
