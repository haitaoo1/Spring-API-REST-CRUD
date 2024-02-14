package springboot.app.springbootcrud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.app.springbootcrud.entities.Product;
import springboot.app.springbootcrud.repositories.ProductoRepository;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductoRepository productRepo;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepo.findAll();
    }

    @Transactional(readOnly= true)
    @Override
    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {
       return productRepo.save(product);
    }
    @Transactional
    @Override
    public Optional<Product> delete(Product product) {
        Optional<Product> prodDb = productRepo.findById(product.getId());
        prodDb.ifPresent(p->{
            productRepo.delete(product);
        });
        return prodDb;
    }
    
}
