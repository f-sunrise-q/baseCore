package com.advance.sunrise.tool.datamask;

import com.advance.sunrise.tool.datamask.annotation.SensitiveInfo;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.lang.annotation.Annotation;

/**
 * 使用Jackson提供的JacksonAnnotationIntrospector实现对注解的禁用
 * 之所以要提供禁用的功能，是因为@SensitiveInfo注解是对所有ObjectMapper都默认开放的，如果不禁用会影响到JsonUtil
 * 和mappingJackson2HttpMessageConverter中的ObjectMapper
 *
 * @author fangqin
 */
public class DisableSensitiveFieldIntrospector extends JacksonAnnotationIntrospector {

    @Override
    public boolean isAnnotationBundle(Annotation ann) {
        if (ann.annotationType().equals(SensitiveInfo.class)) {
            return false;
        } else {
            return super.isAnnotationBundle(ann);
        }
    }


}
