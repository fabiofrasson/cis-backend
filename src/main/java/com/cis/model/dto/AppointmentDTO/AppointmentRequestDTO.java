package com.cis.model.dto.AppointmentDTO;

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
    private UUID room_id;
    private UUID professional_id;
    private UUID patient_id;
    private String observation;
    private Boolean paid;
}
