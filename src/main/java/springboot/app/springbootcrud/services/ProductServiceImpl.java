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
    public Optional<Product> delete(Long id) {
        Optional<Product> prodDb = productRepo.findById(id);
        prodDb.ifPresent(p->{
            productRepo.delete(p);
        });
        return prodDb;
    }

    @Override
    @Transactional
    public Optional<Product> update(Long id, Product product) {
        Optional<Product> productOptional = productRepo.findById(id);
        if (productOptional.isPresent()) {
            Product productDb = productOptional.orElseThrow();
            
            productDb.setSku(product.getSku());
            productDb.setName(product.getName());
            productDb.setDescription(product.getDescription());
            productDb.setPrice(product.getPrice());
            return Optional.of(productRepo.save(productDb));
            
        }
        return productOptional;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsBySku(String username) {
        return productRepo.existsBySku(username);
    }
    
}
