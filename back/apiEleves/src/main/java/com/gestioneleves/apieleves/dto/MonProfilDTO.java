package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonProfilDTO extends UtilisateurDTO {
    private String email;
    private String numTel;
    private Date dateNaissance;
    private Role role;
}
