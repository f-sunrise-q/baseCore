package com.advance.sunrise.tool.datamask.annotation;

import com.advance.sunrise.tool.datamask.IDataMaskHandler;
import com.advance.sunrise.tool.datamask.SensitiveSerializer;
import com.advance.sunrise.tool.datamask.constant.MaskModeEnum;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 敏感信息注解，mode为脱敏模式，主要有两种，全隐藏和隐藏中间30%
 * mode:full 全部隐藏
 * mode:mid 隐藏中间部分
 *
 * @author fangqin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerializer.class)
public @interface SensitiveInfo {

    /**
     * 脱敏模式
     *
     * @return
     */
    MaskModeEnum mode() default MaskModeEnum.FULL;

    /**
     * 自定义脱敏工具
     *
     * @return
     */
    Class<? extends IDataMaskHandler>[] handler() default {};
}
