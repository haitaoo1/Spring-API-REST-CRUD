package springboot.app.springbootcrud.repositories;

import org.springframework.data.repository.CrudRepository;

import springboot.app.springbootcrud.entities.Product;

public interface ProductoRepository extends CrudRepository<Product,Long> {
    boolean existsBySku(String sku);
}
