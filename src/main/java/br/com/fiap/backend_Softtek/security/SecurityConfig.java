package br.com.fiap.backend_Softtek.security;

import br.com.fiap.backend_Softtek.utils.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita a proteção CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define que a sessão não será gerenciada pelo Spring Security
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/softtek-users/users").permitAll() // Permite acesso público a todos os endpoints
                        .requestMatchers("/softtek-users/**").authenticated()
                        .requestMatchers("/softtek-users").authenticated()
                        .requestMatchers("/moods").authenticated()
                        .requestMatchers("/tasks/**").authenticated()
                        .requestMatchers("/progress/**").authenticated()
                        .requestMatchers("/progress").authenticated()
                        .requestMatchers("/questionnaries").authenticated()
                        .anyRequest().authenticated() // Exige autenticação para todos os outros endpoints
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
