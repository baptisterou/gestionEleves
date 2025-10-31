package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VueAdminDTO extends UtilisateurDTO {
    private Role role;
    private String email;
}