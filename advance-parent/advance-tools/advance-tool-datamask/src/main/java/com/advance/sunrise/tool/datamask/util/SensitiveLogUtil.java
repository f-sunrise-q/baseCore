package com.advance.sunrise.tool.datamask.util;

import com.advance.sunrise.tool.datamask.log.LogObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;

/**
 * 敏感日志格式化工具类
 * @author fangqin
 */
public class SensitiveLogUtil {

    private static LogObjectMapper logObjectMapper = new LogObjectMapper();

    /**
     * 将需要记录日志的实体类中带SensitiveInfo注解的字段按照一定的规则脱敏
     *
     * @param obj
     * @return
     */
    public static String parseObject(Object obj){
        try {
            return logObjectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    /**
     * 普通字符串脱敏,隐藏中间30%以上
     *
     * @param maskStr
     * @return
     */
    public static String maskStrInMid(String maskStr){
        if(StringUtils.isEmpty(maskStr)){
            return "";
        }
        int len = maskStr.length();

        int midStart = len / 3;
        int midEnd = len - midStart;
        if (len <= 2) {
            midStart = len - 1;
            midEnd = len;
        }
        String start = maskStr.substring(0, midStart);
        String end = maskStr.substring(midEnd, len);
        StringBuffer sb = new StringBuffer(start);
        if(len - start.length() - end.length()>0) {
            String mid = String.join("", Collections.nCopies(len - start.length() - end.length(), "*"));
            sb.append(mid);
        }
        return sb.append(end).toString();
    }

    /**
     * IP地址脱敏，192.xx*.*.120
     *
     * @param ipStr
     * @return
     */
    public static String maskIpStr(String ipStr){
        if(StringUtils.isEmpty(ipStr)){
            return "";
        }
        String[] strs = ipStr.split("\\.");
        if (strs != null && strs.length > 3) {
            StringBuilder sb = new StringBuilder(12);
            sb.append(strs[0]).append(".").append(strs[1].substring(0, strs[1].length() - 1)).append("*").append(".").append("*.").append(strs[3]);
            return sb.toString();
        }
        return maskStrInMid(ipStr);
    }

    /**
     * 端口号脱敏,首位保留，剩余中间30%以上
     *
     * @param portStr
     * @return
     */
    public static String maskPortStr(String portStr){
        if(StringUtils.isEmpty(portStr)){
            return "";
        }
        int len = portStr.length();
        if(len>1){
            return portStr.substring(0, 1) + maskStrInMid(portStr.substring(1, len));
        }
        return portStr;
    }

    /**
     * 域名脱敏，www.xxxx.com.cn，xxxx隐藏30%以上
     *
     * @param dnsStr
     * @return
     */
    public static String maskDnsStr(String dnsStr){
        if(StringUtils.isEmpty(dnsStr)){
            return "";
        }
        int len = dnsStr.length();
        StringBuilder sb = new StringBuilder();
        String midOriginalStr = dnsStr;
        //是否以协议开头
        if(dnsStr.indexOf("://")>0){
            sb.append(dnsStr.substring(0, dnsStr.indexOf("://")+3));
            midOriginalStr = dnsStr.substring(dnsStr.indexOf("://")+3, len);
        }
        //是否含有www.
        if(midOriginalStr.startsWith("www.")){
            sb.append("www.");
            midOriginalStr = midOriginalStr.substring(4, midOriginalStr.length());
        }
        String end = "";
        //是否带端口
        if(midOriginalStr.indexOf(":")>0){
            String port  = midOriginalStr.substring(midOriginalStr.lastIndexOf(":")+1, midOriginalStr.length());
            end = ":" + maskPortStr(port);

            midOriginalStr  = midOriginalStr.substring(0, midOriginalStr.lastIndexOf(":"));
        }
        //取最后一级域名
        if(midOriginalStr.indexOf(".")>0){

            end = midOriginalStr.substring(midOriginalStr.indexOf("."), midOriginalStr.length());
            midOriginalStr = midOriginalStr.substring(0, midOriginalStr.indexOf("."));
        }
        sb.append(maskStrInMid(midOriginalStr)).append(end);

        return sb.toString();
    }

    /**
     * 手机号脱敏，137******50
     *
     * @param phoneStr
     * @return
     */
    public static String maskPhoneStr(String phoneStr){
        if(StringUtils.isEmpty(phoneStr)){
            return "";
        }else{
            int len = phoneStr.length();
            return phoneStr.substring(0, 3) + "******" + phoneStr.substring(len - 2, len);
        }
    }

    /**
     * 邮箱脱敏，x**@163.com
     *
     * @param emailStr
     * @return
     */
    public static String maskEmailStr(String emailStr){
        if(StringUtils.isEmpty(emailStr)){
            return "";
        }else{
            int len = emailStr.length();
            return emailStr.substring(0, 1) + "**" + emailStr.substring(emailStr.lastIndexOf("@"), len);
        }
    }
}
