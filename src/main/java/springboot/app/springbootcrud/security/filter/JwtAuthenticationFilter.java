package springboot.app.springbootcrud.security.filter;

import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import springboot.app.springbootcrud.entities.User;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    //buscado desde la web de jwt
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    private static final String PREFIX_TOKEN = "Bearer ";
    private static final String HEADER_AUTHORIZATION ="Authorization";

    //creamos el atributo del AuthenticationManager
    private AuthenticationManager authenticationManager;

    //constructor usando el autenticationManager de parametro
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
            //tomar un json y convertirlo a un objeto de java
            User user = null;
            String username = null;
            String password = null;

            //Mapeamos el objeto java   // infomacion de entrada
            //se captura el json usando .readValue(request.getImputStrem()), tipo de objeto a convertir)´
            // añadimos la captura de excepciones
            try {
                user = new ObjectMapper().readValue(request.getInputStream(), User.class);
                username = user.getUsername();
                password = user.getPassword();
            } catch (StreamReadException e) {
                e.printStackTrace();
            } catch (DatabindException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
            // creamos instancia del token usando el user y password del usuario mapeado
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            return authenticationManager.authenticate(authenticationToken);
            //una vez que se autentifica llama al jpaUserDetailsService y le pasa el username y contra
    }

    // si la autenticación sale bien
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
                User user = (User) authResult.getPrincipal();
                String username = user.getUsername();
                String token = Jwts.builder().subject(username).signWith(SECRET_KEY).compact();
            response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

            Map <String, String> body = new HashMap<>();
            body.put("token", token);
            body.put("username", username);
            body.put("message", String.format("Hola %s has iniciado sesion con éxito", username));

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setContentType("application/json");
            response.setStatus(200);
    }
    

    
}
