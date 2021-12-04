package com.cis.model.dto.HeathProfessionalDTO;

import com.cis.model.HealthProfessional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthProfessionalResponseDTO {

    private UUID id;
    private UUID professional_id;
    private String email;
    private String name;
    private String phone;
    private String professionalDocument;


    public HealthProfessionalResponseDTO(HealthProfessional entity){
        this.id = entity.getId();
        this.professional_id = entity.getProfessional_id();
        this.email = entity.getEmail();
        this.name = entity.getName();
        this.professionalDocument = entity.getProfessionalDocument();
    }
}
