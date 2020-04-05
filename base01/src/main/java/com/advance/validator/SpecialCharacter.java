package aaa.bbb.ccc.common.validator.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 特殊字符校验注解
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,ElementType.PARAMETER,ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy ={SpecialCharacterCustomeValidate.class})
public @interface SpecialCharacter {
    String message() default "input.string.special.charactor" ;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String pattern() default "[\\\\/:*?\"<|'>]";
}
