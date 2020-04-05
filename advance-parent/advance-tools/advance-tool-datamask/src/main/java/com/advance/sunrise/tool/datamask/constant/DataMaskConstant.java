package com.advance.sunrise.tool.datamask.constant;

import java.util.regex.Pattern;

public class DataMaskConstant {

    /**
     * IP正则表达式
     */
    public static final String IP_REGEXP = "^((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))$";

    /**
     * 手机号码正则表达式
     */
    public static final String PHONE_REGEXP = "^(\\+86|86)?1((3[0-9])|(4[5|7|9])|(5([0-3]|[5-9]))|(6[6])|(7[0|1|3|5|6|7|8])|(8[0-9])|(9[8|9]))\\d{8}$";

    /**
     * 邮箱正则表达式
     */
    public static final String EMAIL_REGEXP = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    /**
     * 域名正则表达式,可匹配域名、完整url、带参的完整url
     */
    public static final String DNS_REGEXP = "^(?=^.{3,255}$)(http(s)?:\\/\\/)?(www\\.)?[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:\\d+)*(\\/\\w+(\\.)?\\w+)*([\\?&]\\w+=\\w*)*$";

    public static final Pattern IP_PATTERN = Pattern.compile(IP_REGEXP);

    public static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEXP);

    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEXP);

    public static final Pattern DNS_PATTERN = Pattern.compile(DNS_REGEXP);

}
