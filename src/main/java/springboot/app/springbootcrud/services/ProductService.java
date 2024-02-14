package springboot.app.springbootcrud.services;

import java.util.List;
import java.util.Optional;

import springboot.app.springbootcrud.entities.Product;

public interface ProductService {

    //buscar todos los productos
    List<Product> findAll();

    //buscar por id
    Optional<Product> findById(Long id);
    
    Product save(Product product);

    //eliminar un producto
    Optional<Product> delete(Product product);
    
    
} 
