package com.advance.sunrise.tool.datamask;

import com.advance.sunrise.tool.datamask.annotation.SensitiveInfo;
import com.advance.sunrise.tool.datamask.constant.DataMaskConstant;
import com.advance.sunrise.tool.datamask.constant.MaskModeEnum;
import com.advance.sunrise.tool.datamask.util.SensitiveLogUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 序列化工具，主要功能识别注解上的脱敏模式和真正实现隐藏功能
 *
 * @author fangqin
 */
public class SensitiveSerializer extends StdSerializer<Object> implements ContextualSerializer {

    private static Map<String, IDataMaskHandler> handlerMap= new ConcurrentHashMap<>();

    private static final Logger log = LoggerFactory.getLogger(SensitiveSerializer.class);

    private MaskModeEnum mode;

    private IDataMaskHandler dataMaskHandler;

    public SensitiveSerializer() {
        super(Object.class);
    }

    public SensitiveSerializer(MaskModeEnum mode, IDataMaskHandler dataMaskHandler) {
        super(Object.class);
        this.mode = mode;
        this.dataMaskHandler = dataMaskHandler;
    }

    /**
     * createContextual的调用是先于serialize的，createContextual传入了BeanProperty，通过它能获取了到属性的注解
     *
     * @param serializerProvider
     * @param beanProperty
     * @return
     * @throws JsonMappingException
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        MaskModeEnum mode = null;
        IDataMaskHandler dataMaskHandler = null;
        if (beanProperty != null) {
            SensitiveInfo anno = beanProperty.getAnnotation(SensitiveInfo.class);
            if (anno != null) {
                mode = anno.mode();
                Class[] tmp = anno.handler();
                if(tmp!=null && tmp.length>0){
                    try {
                        String className = tmp[0].getName();
                        if(!handlerMap.containsKey(className)){
                            handlerMap.put(className, (IDataMaskHandler) tmp[0].newInstance());
                        }

                        dataMaskHandler = handlerMap.get(className);
                    } catch (Exception e) {
                        log.error("get dataMaskHandler error", e);
                    }
                }
            }
        }
        return new SensitiveSerializer(mode, dataMaskHandler);
    }

    /**
     * 属性的序列化
     *
     * @param o
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (serializerProvider.isEnabled(MapperFeature.USE_ANNOTATIONS)) {
            //dataMaskHandler不为空时，使用自定义处理器
            if(dataMaskHandler!=null){
                jsonGenerator.writeString(dataMaskHandler.mask(o.toString()));
            }else {

                if (MaskModeEnum.MID.equals(mode)) {
                    jsonGenerator.writeString(maskFieldWithMid(o.toString()));
                } else {
                    jsonGenerator.writeString("******");
                }
            }
        } else {
            jsonGenerator.writeString(o == null ? "" : o.toString());
        }
    }

    /**
     * 安全红线中对IP、手机号、邮箱等有特殊要求，其他一般是隐藏至少30%
     *
     * @param originalStr
     * @return
     */
    private String maskFieldWithMid(String originalStr) {
        if (StringUtils.isEmpty(originalStr)) {
            return "";
        }
        if (DataMaskConstant.IP_PATTERN.matcher(originalStr).matches()) {
            //ip:192.xx*.*.120
            return SensitiveLogUtil.maskIpStr(originalStr);
        } else if (DataMaskConstant.PHONE_PATTERN.matcher(originalStr).matches()) {
            //137******50
            return SensitiveLogUtil.maskPhoneStr(originalStr);
        } else if (DataMaskConstant.EMAIL_PATTERN.matcher(originalStr).matches()) {
            //邮箱：首字母和@后缀显示，其余隐藏
            return SensitiveLogUtil.maskEmailStr(originalStr);
        }else if (DataMaskConstant.DNS_PATTERN.matcher(originalStr).matches()){
            return SensitiveLogUtil.maskDnsStr(originalStr);
        }

        return SensitiveLogUtil.maskStrInMid(originalStr);
    }

}
