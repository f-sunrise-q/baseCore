package com.advance.i18n;

import com.advance.util.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 多语言工具类
 *
 **/
public class I18nUtils {

    private static final Logger logger = LoggerFactory.getLogger(I18nUtils.class);

    /**
     * 获得多语言内容，默认根据当前用户的Local获取
     * @param key 多语言key
     * @return 翻译后的值，如获取不到则返回key
     */
    public static String getMessage(String key){
        return getMessage(key,new Object[]{});
    }

    /**
     * 获取多语言内容--带默认值，默认根据当前用户的Local获取
     * @param key 多语言key
     * @param defaultMsg 默认返回值
     * @return 翻译后的值，获取不到返回默认值
     */
    public static String getMessage(String key,String defaultMsg){
        return getMessage(key,defaultMsg,new Object[]{});
    }


    /**
     * 获得多语言内容--带参数,默认根据当前用户的Local获取
     * @param key 多语言key
     * @param params 参数
     * @return 翻译后的值，如获取不到则返回key
     */
    public static String getMessage(String key,Object[] params){
        Locale locale = getLocale();
        if(params == null){
            params = new Object[]{};
        }
        return getMessage(key,params,locale);

    }

    /**
     * 获得多语言内容--带参数--带默认值,默认根据当前用户的Local获取
     * @param key 多语言key
     * @param defaultMsg 默认返回值
     * @param params 参数
     * @return 翻译后的值，获取不到返回默认值
     */
    public static String getMessage(String key,String defaultMsg,Object[] params){
        Locale locale = getLocale();
        return getMessage(key,params,defaultMsg,locale);
    }

    /**
     * 不带参数
     * @param key 多语言key
     * @param locale 语言信息
     * @return 翻译后的值
     */
    public static String getMessage(String key,Locale locale){
        return getMessage(key,new Object[]{},locale);
    }


    /**
     * 获取多语言数据
     * @param key 多语言key
     * @param params 参数
     * @param locale 语言信息
     * @return 翻译后的值
     */
    public static String getMessage(String key,Object[] params,Locale locale){
        try{
            return AppContext.getContext().getMessage(key,params,locale);
        }catch (Exception e){
            logger.error("get locale msg error" ,e);
        }
        return key;
    }

    /**
     * 获取多语言数据
     * @param key 多语言key
     * @param params 参数
     * @param defaultMsg 默认返回值
     * @param locale 语言信息
     * @return 翻译后的值
     */
    public static String getMessage(String key, Object[] params, String defaultMsg, Locale locale) {
        return AppContext.getContext().getMessage(key,params,defaultMsg,locale);
    }

    /**
     * 获取当前语言
     *
     * @return
     */
    private static Locale getLocale(){
        return LocaleContextHolder.getLocale();
    }
}
