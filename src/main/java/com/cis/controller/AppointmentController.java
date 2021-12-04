package com.cis.controller;

import com.cis.exceptions.BadRequestException;
import com.cis.model.Appointment;
import com.cis.model.dto.AppointmentDTO.AppointmentRequestDTO;
import com.cis.model.dto.AppointmentDTO.AppointmentResponseDTO;
import com.cis.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/appointments")
public class AppointmentController {

    @Autowired
    private final AppointmentService service;

    @Autowired
    public AppointmentController(AppointmentService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<Appointment>> list() { return ResponseEntity.ok(service.listAll()); }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<Optional<Appointment>> findByUUID(@PathVariable("uuid") UUID uuid) {
        return new ResponseEntity<>(service.findByUUID(uuid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> create(@RequestBody AppointmentRequestDTO entity){
        try {
            AppointmentResponseDTO appointmentResponseDTO = service.create(entity);
            return new ResponseEntity<>(appointmentResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Appointment> replace(@RequestBody @Valid Appointment appointment) throws  Exception {
        service.update(appointment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
