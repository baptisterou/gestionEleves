package com.gestioneleves.apieleves.controller;

import com.gestioneleves.apieleves.dto.UtilisateurDTO;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.mapper.UtilisateurMapper;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UtilisateurRepository utilisateurRepository;

    @GetMapping("/me")
    public UtilisateurDTO me(@AuthenticationPrincipal Utilisateur currentUser) {
        return UtilisateurMapper.toDto(currentUser);
    }

    @GetMapping
    public Page<UtilisateurDTO> users(@PageableDefault(size = 20, sort = "idUtilisateur") Pageable pageable) {
        return utilisateurRepository.findAll(pageable).map(UtilisateurMapper::toDto);
    }
}
