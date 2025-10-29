package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VueAdminDTO extends UtilisateurDTO {
    private Role role;
}