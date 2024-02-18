package springboot.app.springbootcrud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import springboot.app.springbootcrud.ProductValidation;
import springboot.app.springbootcrud.entities.Product;
import springboot.app.springbootcrud.services.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService pService;
    @Autowired
    private ProductValidation validation;

    @GetMapping
    public List<Product> list(){
        return pService.findAll();
    }

    //Metodo para ver un producto
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id){
        Optional<Product> productOpt =  pService.findById(id);
        if(productOpt.isPresent()){
            return ResponseEntity.ok(productOpt.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    //metodo para insertar prodcto en la BD
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result){
        validation.validate(product, result);
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Product newProduct = pService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
        //personalizado
        //return new ResponseEntity<>("O bjeto creado con éxito", HttpStatus.CREATED);
    }

    //metodo para insertar producto en la BD

    @PutMapping("/{id}")  //BindingResult tiene que ir despues del Objeto(Product)                      
    public  ResponseEntity<?> update(@PathVariable Long id,@Valid
     @RequestBody Product product, BindingResult result){
        validation.validate(product, result);
        if(result.hasFieldErrors()){
            return validation(result);
        }
        
        Optional<Product> OptProduct = pService.findById(id);
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
    



