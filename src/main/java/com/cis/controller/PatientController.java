package com.cis.controller;

import com.cis.exceptions.ResourceNotFoundException;
import com.cis.model.dto.PatientDTO.PatientCreationDTO;
import com.cis.model.dto.PatientDTO.PatientReturnDTO;
import com.cis.model.dto.PatientDTO.PatientUpdateDTO;
import com.cis.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PatientController {

  private PatientService service;

  @GetMapping
  public ResponseEntity<Page<PatientReturnDTO>> list(Pageable pageable) {
    return ResponseEntity.ok(service.listAll(pageable));
  }

  // mudar retorno para DTO
  @GetMapping(path = "/{id}")
  public ResponseEntity<PatientReturnDTO> findById(@PathVariable("id") UUID id) {
    return ResponseEntity.ok(service.findByIdOrThrowError(id));
  }

  // mudar retorno para DTO
  @GetMapping(path = "/find")
  public ResponseEntity<PatientReturnDTO> findByEmail(@RequestParam("email") String email) {
    return ResponseEntity.ok(service.findByEmailOrThrowError(email));
  }

  @PostMapping
  public ResponseEntity<PatientReturnDTO> save(@RequestBody @Valid PatientCreationDTO patient)
      throws Exception {
    return new ResponseEntity<>(service.save(patient), HttpStatus.CREATED);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
    return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody PatientUpdateDTO patient)
      throws Exception {
    PatientReturnDTO dto = service.findByIdOrThrowError(id);
    if (dto.getId() == null) {
      throw new ResourceNotFoundException(
          "Paciente não encontrado, por favor revise o ID enviado.");
    }
    return new ResponseEntity<>(service.update(id, patient), HttpStatus.OK);
  }
}
