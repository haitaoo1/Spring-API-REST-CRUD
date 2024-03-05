package springboot.app.springbootcrud.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import springboot.app.springbootcrud.Validation.IsExistsDb;
import springboot.app.springbootcrud.Validation.IsRequired;

@Entity
@Table(name= "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    //not null solo para objetos
    @NotEmpty(message = "{NotEmpty.product.name}")
    @Size(min = 3, max= 30)
    private String name;

    @Min(value = 200, message = "{Min.product.prices}")
    @NotNull(message = "{NotNull.product.price}")
    private Integer price;

    @NotBlank(message = "{NotBlank.product.description}")
    private String description;

    @IsRequired
    @IsExistsDb
    private String sku;

    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer prices) {
        this.price = prices;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


}
