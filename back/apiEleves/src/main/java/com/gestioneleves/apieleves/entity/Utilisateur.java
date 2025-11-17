package com.gestioneleves.apieleves.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString(exclude = {"eleves", "classesEnseignant", "matieresEnseignant", "inscriptionEffectuees"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "utilisateur")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Utilisateur implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_utilisateur", nullable = false)
    @EqualsAndHashCode.Include
    private Long idUtilisateur;
    @Column(name="nom", nullable = false)
    private String nom;
    @Column(name="prenom" , nullable = false)
    private String prenom;
    @Column(name="email",unique = true, length = 100, nullable = false)
    private String email;
    @Column(name="mot_de_passe", nullable = false)
    @JsonIgnore
    private String motDePasse;
    @Column(name="date_naissance", nullable = false)
    private LocalDate dateNaissance;
    @Column(name="num_tel", nullable = false)
    private String numTel;
    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private Role role;
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "utilisateur")
    private List<Eleve> eleves = new ArrayList<>();

    @OneToMany(mappedBy = "utilisateur")
    private List<Inscription> inscriptions = new ArrayList<>();

    @OneToMany(mappedBy = "utilisateur")
    private List<Representation> representations = new ArrayList<>();

    @OneToMany(mappedBy = "enseignant")
    private List<Enseignement> enseignements = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) return Collections.emptyList();
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
