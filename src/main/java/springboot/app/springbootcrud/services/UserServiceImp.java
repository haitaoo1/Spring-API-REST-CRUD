package springboot.app.springbootcrud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.app.springbootcrud.entities.Role;
import springboot.app.springbootcrud.entities.User;
import springboot.app.springbootcrud.repositories.RoleRepository;
import springboot.app.springbootcrud.repositories.UserRepository;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;  

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {
        //ASIGNAR ROL USER (A TODOS)
       Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
       List<Role> roles = new ArrayList<>();
       userRole.ifPresent(roles::add);

        if(user.isAdmin()){
            Optional<Role> optRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optRoleAdmin.ifPresent(roles::add);
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
       return userRepository.existsByUsername(username);
    }
    
}
