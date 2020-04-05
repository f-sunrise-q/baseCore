package aaa.bbb.ccc.common.validator.annotation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @descrption: 特殊字符校验
 * @author: wangji
 * @date: 2018-03-28 15:51
 */
public class SpecialCharacterCustomeValidate implements ConstraintValidator<SpecialCharacter, String> {
    private String pattern = "";

    @Override
    public void initialize(SpecialCharacter constraintAnnotation) {
        pattern = constraintAnnotation.pattern();
    }


    /**
     *
     * @param value
     * @param context
     * @return  true 不含特殊字符  false 含特殊字符
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = true;
        if (StringUtils.isNotEmpty(value)) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            result = !m.find();
        }
        return result;
    }
}
