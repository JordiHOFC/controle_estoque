package br.com.controleestoque.ControleEstoque.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExisteProdutoValidator.class)
public @interface ExisteProduto {
    String message() default "Não existe registro de produto com este identificador";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
