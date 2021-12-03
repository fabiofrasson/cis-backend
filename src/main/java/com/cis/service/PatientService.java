package com.cis.service;

import com.cis.exceptions.BadRequestException;
import com.cis.model.Patient;
import com.cis.model.dto.PatientCreationDTO;
import com.cis.model.dto.PatientReturnDTO;
import com.cis.model.dto.PatientUpdateDTO;
import com.cis.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

  private PatientRepository repository;

  public PatientService(PatientRepository repository) {
    this.repository = repository;
  }

  public Page<PatientReturnDTO> listAll(Pageable pageable) {
    Page<Patient> patients = repository.findAll(pageable);

    List<PatientReturnDTO> patientsList = new ArrayList<>();

    for (Patient patient : patients) {
      patientsList.add(new PatientReturnDTO(patient));
    }

    return new PageImpl<>(patientsList);
  }

  public PatientReturnDTO findByIdOrThrowError(UUID id) {
    Optional<Patient> patient = repository.findById(id);

    if (patient.isEmpty()) {
      throw new BadRequestException("Paciente não encontrado.");
    } else {
      Patient patient1 = patient.get();
      return new PatientReturnDTO(patient1);
    }
  }

  public PatientReturnDTO findByEmailOrThrowError(String email) {

    Patient foundPatient = repository.findByEmailIgnoreCase(email);

    if (foundPatient != null) {
      return new PatientReturnDTO(foundPatient);
    } else {
      throw new BadRequestException("Paciente não encontrado.");
    }
  }

  @Transactional
  public PatientReturnDTO save(PatientCreationDTO patientCreationDTO) {
    Patient findPatient = repository.findByEmailIgnoreCase(patientCreationDTO.getEmail());
    if (findPatient != null) {
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
    repository.deleteById(id);
    return "Registro deletado com sucesso!";
  }

  public String update(UUID id, PatientUpdateDTO patient) {

    Patient savedPatient = repository.getById(id);

    savedPatient.setName(patient.getName());
    savedPatient.setEmail(patient.getEmail());
    savedPatient.setPhone(patient.getPhone());
    savedPatient.setGender(patient.getGender());

    repository.save(savedPatient);
    return "Registro atualizado com sucesso!";
  }
}
