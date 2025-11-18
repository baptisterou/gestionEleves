package com.gestioneleves.apieleves.auth;

import com.gestioneleves.apieleves.auth.dto.AuthRequest;
import com.gestioneleves.apieleves.auth.dto.AuthResponse;
import com.gestioneleves.apieleves.auth.dto.SignupRequest;
import com.gestioneleves.apieleves.entity.Role;
import com.gestioneleves.apieleves.entity.Utilisateur;
import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import com.gestioneleves.apieleves.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur d'authentification exposant les endpoints de création de compte et de connexion.
 *
 * Endpoints:
 * - POST /auth/signup : inscription d'un utilisateur (crée un compte avec le rôle RESPONSABLE par défaut)
 * - POST /auth/login  : authentification et délivrance d'un jeton JWT
 *
 * Sécurité:
 * - Ces endpoints sont publics (voir {@link com.gestioneleves.apieleves.security.ApplicationSecurityConfig}).
 * - Le jeton JWT retourné doit être fourni via l'en-tête Authorization: Bearer <token> pour les appels protégés.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Inscription d'un nouvel utilisateur et génération d'un JWT.
     *
     * Remarques sécurité:
     * - Le rôle envoyé n'est pas pris en compte: le rôle est forcé à {@link com.gestioneleves.apieleves.entity.Role#RESPONSABLE}.
     * - En cas d'email déjà existant, retourne 400 Bad Request (corps vide).
     *
     * @param request données d'inscription validées
     * @return un {@link AuthResponse} contenant le token JWT
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        if (utilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Utilisateur user = new Utilisateur();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setMotDePasse(passwordEncoder.encode(request.getPassword()));
        user.setDateNaissance(request.getDateNaissance());
        user.setNumTel(request.getNumTel());
        // For security reasons, ignore any incoming role from signup and force RESPONSABLE
        user.setRole(Role.RESPONSABLE);
        Utilisateur saved = utilisateurRepository.save(user);
        String token = jwtService.generateToken(saved.getUsername(), saved.getAuthorities());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Authentifie un utilisateur existant et génère un JWT.
     *
     * @param request email et mot de passe
     * @return un {@link AuthResponse} contenant le token JWT si l'authentification réussit
     * @throws UsernameNotFoundException en cas d'échec d'authentification
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        if (!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Authentication failed");
        }
        String token = jwtService.generateToken(request.getEmail(), authentication.getAuthorities());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
