package springboot.app.springbootcrud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import springboot.app.springbootcrud.entities.User;
import springboot.app.springbootcrud.repositories.UserRepository;

public class JpaUserDetailsService implements UserDetailsService{

    //cada vez que se haga login se va utilizar esta clase 

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = repository.findByUsername(username);
        if(optionalUser.isEmpty()){
           throw new UsernameNotFoundException(String.format("Username %s no existe en el sitema", username));
        }
        
        User user = optionalUser.orElseThrow();
        List<GrantedAuthority> authorities =  user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), user.isEnabled(),
            true,true,true, 
            authorities);
    }
    
}
