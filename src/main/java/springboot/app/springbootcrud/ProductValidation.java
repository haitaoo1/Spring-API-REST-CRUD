package springboot.app.springbootcrud;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import springboot.app.springbootcrud.entities.Product;

@Component
public class ProductValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    //errors es el BindingResult
    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name","null", "es requerido");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotBlank.product.description");
        //otra forma
        if(product.getDescription() == null || product.getDescription().isBlank()){
            errors.rejectValue("description", "es requerido, por favor!");
        }

        //MENSAJES PERSONALIZADOS USANDO LA CLASE VALIDATION
        if(product.getPrice() == null ){
            errors.rejectValue("price", "null", "no puede ser nulo, okk");
        }else if(product.getPrice() < 300 ){
            errors.rejectValue("price", "null", "debe ser un valor mayor o igual q");
        }
    }

}
