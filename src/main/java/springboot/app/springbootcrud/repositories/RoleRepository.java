package springboot.app.springbootcrud.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import springboot.app.springbootcrud.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);


}