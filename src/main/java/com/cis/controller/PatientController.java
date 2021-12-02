package com.cis.controller;

import com.cis.model.Patient;
import com.cis.model.dto.PatientCreationDTO;
import com.cis.model.dto.PatientReturnDTO;
import com.cis.model.dto.PatientUpdateDTO;
import com.cis.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PatientController {

  private PatientService service;

  @GetMapping
  public ResponseEntity<Page<Patient>> list(Pageable pageable) {
    return ResponseEntity.ok(service.listAll(pageable));
  }

  // mudar retorno para DTO
  @GetMapping(path = "/{id}")
  public ResponseEntity<Patient> findById(@PathVariable("id") UUID id) {
    return ResponseEntity.ok(service.findByIdOrThrowError(id));
  }

  // mudar retorno para DTO
  @GetMapping(path = "/find")
  public ResponseEntity<Optional<Patient>> findByEmail(@RequestParam("email") String email) {
    return ResponseEntity.ok(service.findByEmailOrThrowError(email));
  }

  @PostMapping
  public ResponseEntity<PatientReturnDTO> save(@RequestBody @Valid PatientCreationDTO patient) {
    return new ResponseEntity<>(service.save(patient), HttpStatus.CREATED);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
    return new ResponseEntity<>(service.delete(id), HttpStatus.NO_CONTENT);
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity<String> update(
      @RequestParam("id") UUID id, @RequestBody PatientUpdateDTO patient) {
    return new ResponseEntity<>(service.update(id, patient), HttpStatus.NO_CONTENT);
  }
}
