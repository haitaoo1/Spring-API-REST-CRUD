package springboot.app.springbootcrud.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import springboot.app.springbootcrud.services.UserService;

@Component
public class ExistsByUsernameValidation implements ConstraintValidator<ExistsByUsername, String> {

    @Autowired
    private UserService service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !service.existsByUsername(username);
    }
    
    
}
