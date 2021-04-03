package br.com.controleestoque.ControleEstoque.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = QuantidadeDisponivelValidator.class)
public @interface QuantidadeDisponivel {
    String message() default "Quantidade indisponivel";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
