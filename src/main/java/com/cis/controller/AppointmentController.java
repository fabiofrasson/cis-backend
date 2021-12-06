package com.cis.controller;

import com.cis.exceptions.BadRequestException;
import com.cis.model.Appointment;
import com.cis.model.dto.AppointmentDTO.AppointmentRequestDTO;
import com.cis.service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/appointments")
public class AppointmentController {

  private final AppointmentService service;

  public AppointmentController(AppointmentService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<Page<Appointment>> list(Pageable pageable) {
    return ResponseEntity.ok(service.listAll(pageable));
  }

  @GetMapping(path = "/{uuid}")
  public ResponseEntity<Appointment> findByUUID(@PathVariable("uuid") UUID uuid) {
    return new ResponseEntity<>(service.findByUUID(uuid), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> create(@RequestBody AppointmentRequestDTO entity) {
    try {
      var appointmentResponseDTO = service.create(entity);
      return new ResponseEntity<>(appointmentResponseDTO, HttpStatus.OK);
    } catch (Exception e) {
      throw new BadRequestException(e.getMessage());
    }
  }

  @PutMapping
  public ResponseEntity<Appointment> replace(@RequestBody @Valid Appointment appointment)
      throws Exception {
    service.update(appointment);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
