package springboot.app.springbootcrud.repositories;

import org.springframework.data.repository.CrudRepository;

import springboot.app.springbootcrud.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    
}
