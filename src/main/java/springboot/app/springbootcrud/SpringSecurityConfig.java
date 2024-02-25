package springboot.app.springbootcrud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
    //Encriptar la contraseña
    @Bean  // componente para inyectar
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //PARA DEJAR PUBLICO LA RUTA /users sin esto no 
    //
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests((authz) -> authz
        .requestMatchers(HttpMethod.GET, "api/users").permitAll()
        .requestMatchers(HttpMethod.POST, "api/users/register").permitAll()
        .anyRequest().authenticated())
        .csrf(config -> config.disable())
        .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
    }
    

}
