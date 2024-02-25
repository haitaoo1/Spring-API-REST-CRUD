package springboot.app.springbootcrud.repositories;

import org.springframework.data.repository.CrudRepository;

import springboot.app.springbootcrud.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByUsername(String username);
}
