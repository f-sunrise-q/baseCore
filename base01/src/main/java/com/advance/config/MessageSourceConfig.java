package com.advance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * Created by qin on 20/3/19.
 */
@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {

    @Value("${i18n.meessages.path:}")
    private String messagesPath;

    @Bean(name = "messageSource")
    @ConditionalOnMissingBean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource rbms = new ReloadableResourceBundleMessageSource();
        if(!StringUtils.isEmpty(messagesPath)){
            //设置path
            rbms.addBasenames(messagesPath.split(","));
        }
        rbms.setDefaultEncoding("UTF-8");
        rbms.setUseCodeAsDefaultMessage(true);
        rbms.setFallbackToSystemLocale(false);
        return rbms;
    }

    /**
     * 使用session存储语言信息
     *
     * @return
     */
//    @Bean
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver slr = new SessionLocaleResolver();
//        slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
//        return slr;
//    }

    /**
     * 使用cookie存储语言信息ß
     *
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("language");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
