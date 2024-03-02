package springboot.app.springbootcrud.security.filter;

import static springboot.app.springbootcrud.security.TokenJwtConfig.CONTENT_TYPE;
import static springboot.app.springbootcrud.security.TokenJwtConfig.HEADER_AUTHORIZATION;
import static springboot.app.springbootcrud.security.TokenJwtConfig.PREFIX_TOKEN;
import static springboot.app.springbootcrud.security.TokenJwtConfig.SECRET_KEY;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import springboot.app.springbootcrud.security.SimpleGrantedAuthorityJsonCreator;

public class JwtValidationFilter extends BasicAuthenticationFilter{

    //para validar el token 
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(HEADER_AUTHORIZATION);

        // en caso de que nos pasen un token  que no tenga el autorization y el bearer nos salimos
        if(header == null || header.startsWith(PREFIX_TOKEN)){
            return;
        }
        //quitamos el bearer de la cabecera, solo queremos el token
        String token = header.replace(PREFIX_TOKEN, "");
        //validamos                  //verificamos la llave,
        try{
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String username = claims.getSubject();
            //otra manera  para sacar el username ( en jwtAutFilter hemos hecho Claims.add("username", username))
            //String username2 = (String) claims.get("username");
            Object authoritiesClaims = claims.get("authorities");
            //autenticar
            Collection<? extends GrantedAuthority> authorities =
                Arrays.asList( new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
                .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

            UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);


        } catch(JwtException e){
            Map<String,String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El token es invalido");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);
        }
        
    }
    
}