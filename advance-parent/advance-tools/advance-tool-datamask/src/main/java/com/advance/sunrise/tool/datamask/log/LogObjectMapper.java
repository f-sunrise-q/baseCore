package com.advance.sunrise.tool.datamask.log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = 1L;

    public LogObjectMapper() {
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
