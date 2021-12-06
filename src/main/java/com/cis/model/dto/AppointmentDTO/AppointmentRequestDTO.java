package com.cis.model.dto.AppointmentDTO;

import com.cis.model.Appointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequestDTO {
  private Date date;
  private Integer hour;
  private Integer minute;
  private UUID roomId;
  private UUID professionalId;
  private UUID patientId;
  private String observation;
  private Boolean paid;
  private Double fee;

  public AppointmentRequestDTO(Appointment appointment) {
    this.date = appointment.getDate();
    this.hour = appointment.getHour();
    this.minute = appointment.getMinute();
    this.roomId = appointment.getRoom().getId();
    this.professionalId = appointment.getProfessional().getId();
    this.patientId = appointment.getPatient().getId();
    this.observation = appointment.getObservation();
    this.paid = appointment.getPaid();
    this.fee = appointment.getFee();
  }
}
