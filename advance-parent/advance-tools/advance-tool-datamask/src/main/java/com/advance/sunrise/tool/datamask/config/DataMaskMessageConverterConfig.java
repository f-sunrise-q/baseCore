package com.advance.sunrise.tool.datamask.config;

import com.advance.sunrise.tool.datamask.DisableSensitiveFieldIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.annotation.PostConstruct;

@Configuration
public class DataMaskMessageConverterConfig {

    @Value("${advance.tool.datamask.close:false}")
    private boolean closed;

    @Autowired(required = false)
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @PostConstruct
    public void init(){
        if(mappingJackson2HttpMessageConverter!=null && closed) {
            ObjectMapper objectMapper = mappingJackson2HttpMessageConverter.getObjectMapper();
            objectMapper.setAnnotationIntrospector(new DisableSensitiveFieldIntrospector());
        }
    }
}
