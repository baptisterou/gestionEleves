package com.gestioneleves.apieleves.dto;

import com.gestioneleves.apieleves.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MonProfilDTO extends UtilisateurDTO {
    private String email;
    private String numTel;
    private Date dateNaissance;
    private Role role;
}
