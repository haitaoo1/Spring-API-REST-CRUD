package springboot.app.springbootcrud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import springboot.app.springbootcrud.entities.User;
import springboot.app.springbootcrud.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userServices;

    @GetMapping
    public List <User> list(){
        return userServices.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userServices.save(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        user.setAdmin(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(userServices.save(user));
    }

    private ResponseEntity<?> validation(BindingResult result){
        Map<String,String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errors.put(err.getField(), "el campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    
}
