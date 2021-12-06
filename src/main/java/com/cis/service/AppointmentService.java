package com.cis.service;

import com.cis.exceptions.BadRequestException;
import com.cis.exceptions.ResourceNotFoundException;
import com.cis.model.Appointment;
import com.cis.model.HealthProfessional;
import com.cis.model.Patient;
import com.cis.model.Room;
import com.cis.model.dto.AppointmentDTO.AppointmentRequestDTO;
import com.cis.model.dto.AppointmentDTO.AppointmentResponseDTO;
import com.cis.repository.AppointmentRepository;
import com.cis.repository.HealthProfessionalRepository;
import com.cis.repository.PatientRepository;
import com.cis.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {
  private final AppointmentRepository appointmentRepository;
  private final RoomRepository roomRepository;
  private final PatientRepository patientRepository;
  private final HealthProfessionalRepository professionalRepository;

  public AppointmentService(
      AppointmentRepository appointmentRepository,
      RoomRepository roomRepository,
      PatientRepository patientRepository,
      HealthProfessionalRepository professionalRepository) {
    this.appointmentRepository = appointmentRepository;
    this.roomRepository = roomRepository;
    this.patientRepository = patientRepository;
    this.professionalRepository = professionalRepository;
  }

  public Page<Appointment> listAll(Pageable pageable) {
    return appointmentRepository.findAll(pageable);
  }

  public Appointment findByUUID(UUID uuid) {

    return appointmentRepository
        .findById(uuid)
        .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada."));
  }

  public Appointment findByDate(Date date) {

    return appointmentRepository
        .findByDate(date)
        .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada."));
  }

  public Appointment findByHour(Integer hour) {

    return appointmentRepository
        .findByHour(hour)
        .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada."));
  }

  public Appointment findByMinute(Integer minute) {
    return appointmentRepository
        .findByMinute(minute)
        .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada."));
  }

  public Appointment findByProfessionalId(UUID professionalId) {
    return appointmentRepository
        .findByProfessionalId(professionalId)
        .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada."));
  }

  public Appointment findByRoomId(UUID roomId) {
    return appointmentRepository
        .findByRoomId(roomId)
        .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada."));
  }

  public Appointment findByPatientId(UUID patientId) {
    return appointmentRepository
        .findByPatientId(patientId)
        .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada."));
  }

  public Appointment findByBooking(
      Date date, Integer hour, Integer minute, UUID patient, UUID professional) {
    return appointmentRepository
        .findAppointmentByDateAndAndHourAndMinuteAndPatientAndProfessional(
            date, hour, minute, patient, professional)
        .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado"));
  }

  @Transactional
  public String create(AppointmentRequestDTO entity) {

    // Variáveis de Verificação
    Optional<Appointment> busyHour = appointmentRepository.findByHour(entity.getHour());
    Optional<Appointment> busyMinute = appointmentRepository.findByMinute(entity.getMinute());
    Optional<Appointment> busyProfessional =
        appointmentRepository.findByProfessionalId(entity.getProfessionalId());
    Optional<Appointment> busyRoom = appointmentRepository.findByRoomId(entity.getRoomId());
    Optional<Appointment> busyPatient =
        appointmentRepository.findByPatientId(entity.getPatientId());

    // Verificação se o Professinal já está com algum agendamento para esse horário FALTA COLOCAR O
    // DIA
    if (busyHour.isPresent() && busyMinute.isPresent() && busyProfessional.isPresent()) {
      throw new BadRequestException("Professional already busy for this time");
    }

    // Verificação se o Paciente já está com algum agendamento para esse horário
    if (busyHour.isPresent() && busyMinute.isPresent() && busyPatient.isPresent()) {
      throw new BadRequestException("Patient already busy for this time");
    }

    // Verificação se a Sala já está com algum agendamento para esse horário
    if (busyHour.isPresent() && busyMinute.isPresent() && busyRoom.isPresent()) {
      throw new BadRequestException("Room already busy for this time");
    }

    Room room =
        roomRepository
            .findById(entity.getRoomId())
            .orElseThrow(() -> new BadRequestException("Room not found"));
    Patient patient =
        patientRepository
            .findById(entity.getPatientId())
            .orElseThrow(() -> new BadRequestException("Patient not found"));
    HealthProfessional professional =
        professionalRepository
            .findById(entity.getProfessionalId())
            .orElseThrow(() -> new BadRequestException("Professional not found"));

    Appointment appointmentToBeSaved = new Appointment();
    appointmentToBeSaved.setDate(entity.getDate());

    // Verificação se a hora é válida
    if (entity.getHour() >= 0 && entity.getHour() < 24) {
      appointmentToBeSaved.setHour(entity.getHour());
    } else {
      throw new BadRequestException("Hour not valid");
    }

    // Verificação se o minuto é válido
    if (entity.getMinute() >= 0 && entity.getMinute() <= 59) {
      appointmentToBeSaved.setMinute(entity.getMinute());
    } else {
      throw new BadRequestException("Minute not valid");
    }

    appointmentToBeSaved.setObservation(entity.getObservation());
    appointmentToBeSaved.setPaid(entity.getPaid());
    appointmentToBeSaved.setRoom(room);
    appointmentToBeSaved.setPatient(patient);
    appointmentToBeSaved.setProfessional(professional);
    appointmentToBeSaved.setId(UUID.randomUUID());

    Appointment save = appointmentRepository.save(appointmentToBeSaved);

    return "Appointment Created with Success";
  }

  public String update(UUID id, AppointmentRequestDTO entity) {
    Appointment appointment =
        appointmentRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestException("Appointment Not Found"));

    Room room =
        roomRepository
            .findById(entity.getRoomId())
            .orElseThrow(() -> new BadRequestException("Room not found"));
    Patient patient =
        patientRepository
            .findById(entity.getPatientId())
            .orElseThrow(() -> new BadRequestException("Patient not found"));
    HealthProfessional professional =
        professionalRepository
            .findById(entity.getProfessionalId())
            .orElseThrow(() -> new BadRequestException("Professional not found"));

    appointment.setHour(entity.getHour());
    appointment.setMinute(entity.getMinute());
    appointment.setRoom(room);
    appointment.setPatient(patient);
    appointment.setProfessional(professional);

    Appointment save = appointmentRepository.save(appointment);

    return ("Appointment modified with success");
  }

  public String delete(UUID id) {
    appointmentRepository.deleteById(id);
    return "Registro deletado com sucesso!";
  }

  public AppointmentResponseDTO update(Appointment appointment) {
    Optional<Appointment> appointmentToChange = appointmentRepository.findById(appointment.getId());

    if (appointmentToChange.isEmpty()) {
      throw new BadRequestException("Consulta não encontrada");
    } else {
      return new AppointmentResponseDTO(appointmentRepository.save(appointment));
    }
  }
}
