package com.advance.sunrise.tool.datamask;

public interface IDataMaskHandler {

    /**
     * 对data进行脱敏
     *
     * @param data
     * @return
     */
    String mask(String data);
}
