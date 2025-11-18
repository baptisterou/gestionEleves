package com.gestioneleves.apieleves.security;

import com.gestioneleves.apieleves.repository.UtilisateurRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuration Spring Security principale de l'API.
 *
 * Points clés:
 * - Authentification stateless par JWT (voir {@link JwtAuthenticationFilter} et {@link JwtService})
 * - Gestion centralisée des erreurs d'authentification/autorisations en JSON
 *   via {@link JsonAuthenticationEntryPoint} et {@link JsonAccessDeniedHandler}
 * - Règles d'autorisation par ressource HTTP et rôle (ADMIN/ENSEIGNANT/RESPONSABLE)
 * - CORS activé et configuré pour les frontends de développement
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApplicationSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UtilisateurRepository utilisateurRepository;
    private final JsonAuthenticationEntryPoint authenticationEntryPoint;
    private final JsonAccessDeniedHandler accessDeniedHandler;

    public ApplicationSecurityConfig(@Lazy JwtAuthenticationFilter jwtAuthFilter,
                                     UtilisateurRepository utilisateurRepository,
                                     JsonAuthenticationEntryPoint authenticationEntryPoint,
                                     JsonAccessDeniedHandler accessDeniedHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.utilisateurRepository = utilisateurRepository;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> utilisateurRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    /**
     * Expose l'AuthenticationManager fourni par Spring pour les endpoints d'authentification.
     */
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    /**
     * Chaîne de filtres de sécurité:
     * - CSRF désactivé (API stateless)
     * - CORS activé
     * - Sessions sans état
     * - Règles d'autorisations par endpoint
     * - Filtre JWT avant l'authentification standard
     */
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/users/**").authenticated()

                        // Eleves
                        .requestMatchers(HttpMethod.GET, "/api/eleve/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/eleve/**").hasAnyRole("ADMIN", "RESPONSABLE")
                        .requestMatchers(HttpMethod.PUT, "/api/eleve/**").hasAnyRole("ADMIN", "RESPONSABLE")
                        .requestMatchers(HttpMethod.DELETE, "/api/eleve/**").hasRole("ADMIN")

                        // Notes
                        .requestMatchers(HttpMethod.GET, "/api/note/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/note/**").hasAnyRole("ADMIN", "ENSEIGNANT")
                        .requestMatchers(HttpMethod.PUT, "/api/note/**").hasAnyRole("ADMIN", "ENSEIGNANT")
                        .requestMatchers(HttpMethod.DELETE, "/api/note/**").hasRole("ADMIN")

                        // Matieres
                        .requestMatchers(HttpMethod.GET, "/api/matiere/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/matiere/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/matiere/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/matiere/**").hasRole("ADMIN")

                        // Classes
                        .requestMatchers(HttpMethod.GET, "/api/classe/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/classe/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/classe/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/classe/**").hasRole("ADMIN")

                        // Inscriptions
                        .requestMatchers(HttpMethod.GET, "/api/inscription/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/inscription/**").hasAnyRole("ADMIN", "RESPONSABLE")
                        .requestMatchers(HttpMethod.DELETE, "/api/inscription/**").hasAnyRole("ADMIN", "RESPONSABLE")

                        // Bulletins
                        .requestMatchers(HttpMethod.GET, "/api/bulletin/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/bulletin/**").hasAnyRole("ADMIN", "ENSEIGNANT")
                        .requestMatchers(HttpMethod.PUT, "/api/bulletin/**").hasAnyRole("ADMIN", "ENSEIGNANT")
                        .requestMatchers(HttpMethod.DELETE, "/api/bulletin/**").hasRole("ADMIN")

                        // Utilisateurs
                        // Admin-only views with role
                        .requestMatchers(HttpMethod.GET, "/api/utilisateur/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/utilisateur/*/admin").hasRole("ADMIN")
                        // General user listing/details (public DTO)
                        .requestMatchers(HttpMethod.GET, "/api/utilisateur/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/utilisateur/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/utilisateur/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/utilisateur/**").hasRole("ADMIN")

                        // fallback generic rules
                        .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                        .anyRequest().denyAll()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    /**
     * Source de configuration CORS utilisée par Spring Security.
     */
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:5173",
                "http://localhost:4200"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With"));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Disposition"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
