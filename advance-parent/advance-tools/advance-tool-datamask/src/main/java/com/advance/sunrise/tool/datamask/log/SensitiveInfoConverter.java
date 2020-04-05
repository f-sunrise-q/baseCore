package com.advance.sunrise.tool.datamask.log;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.advance.sunrise.tool.datamask.util.SensitiveLogUtil;
import org.slf4j.helpers.MessageFormatter;

/**
 * 让敏感信息的脱敏转换器直接在logback.xml中配置
 */
public class SensitiveInfoConverter extends MessageConverter {

    @Override
    public String convert(ILoggingEvent event) {
        return getSensitiveFormatMsg(event.getMessage(), event.getFormattedMessage(), event.getArgumentArray());
    }

    private String getSensitiveFormatMsg(String message, String formattedMessage, Object[] params) {
        String result = formattedMessage;
        if (params != null) {
            String[] pStrs = new String[params.length];
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof String) {
                    pStrs[i] = (String) params[i];
                } else {
                    pStrs[i] = SensitiveLogUtil.parseObject(params[i]);
                }
            }
            result = MessageFormatter.arrayFormat(message, pStrs).getMessage();
        }
        return result;
    }
}
