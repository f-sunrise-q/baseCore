package com.advance.sunrise.tool.datamask.constant;

public enum MaskModeEnum {
    /**
     * 全部隐藏
     */
    FULL("full"),

    /**
     * 隐藏中间部分
     */
    MID("mid");


    MaskModeEnum(String maskMode) {
        this.maskMode = maskMode;
    }

    private String maskMode;

    public String getMaskMode() {
        return maskMode;
    }
}
