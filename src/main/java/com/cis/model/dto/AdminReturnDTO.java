package com.cis.model.dto;

import com.cis.model.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminReturnDTO {

    private UUID id;
    private String name;
    private String email;

    public AdminReturnDTO(Admin admin) {
        this.id = admin.getId();
        this.name = admin.getName();
        this.email = admin.getEmail();
    }
}
