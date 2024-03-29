package springboot.app.springbootcrud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import springboot.app.springbootcrud.ProductValidation;
import springboot.app.springbootcrud.entities.Product;
import springboot.app.springbootcrud.services.ProductService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


//la ruta de front es diferente 
//para que el front(angular) pueda consumir la api de back
@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
//para cualquier ruta
//@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService pService;
    // @Autowired
    // private ProductValidation validation;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public List<Product> list(){
        return pService.findAll();
    }

    //Metodo para ver un producto
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> view(@PathVariable Long id){
        Optional<Product> productOpt =  pService.findById(id);
        if(productOpt.isPresent()){
            return ResponseEntity.ok(productOpt.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    //metodo para insertar prodcto en la BD
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result){
       // validation.validate(product, result);
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Product newProduct = pService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
        //personalizado
        //return new ResponseEntity<>("O bjeto creado con éxito", HttpStatus.CREATED);
    }

    //metodo para insertar producto en la BD
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")  //BindingResult tiene que ir despues del Objeto(Product)                      
    public  ResponseEntity<?> update(@PathVariable Long id,@Valid
     @RequestBody Product product, BindingResult result){
       // validation.validate(product, result);
        if(result.hasFieldErrors()){
            return validation(result);
        }
        
        Optional<Product> OptProduct = pService.update(id, product);
        if(OptProduct.isPresent()){

            return ResponseEntity.status(HttpStatus.CREATED).body(pService.save(product));
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errors.put(err.getField(), "el campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    @PreAuthorize("hasRole('ADMIN')")
    //metodo para eliminar en la BD
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Product> productDl = pService.delete(id);
        if (productDl.isPresent()) {
            return ResponseEntity.ok(productDl.orElseThrow());          
        }
        return ResponseEntity.notFound().build();
    }



}
    



