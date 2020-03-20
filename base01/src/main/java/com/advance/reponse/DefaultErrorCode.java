package com.advance.reponse;

/**
 * 默认错误码
 *
 * Created by qin on 20/3/19.
 */
public enum DefaultErrorCode implements IErrorCode{

    SUCCESS("0","success"),

    /**
     * 未归类错误码 ,错误码中 前16位的后四位，错误标识，在这里直接定义
     */
    ERROR("10001", "unkown.error"),

    /**
     * 参数校验错误
     */
    VALIDATE_ERROR("10002", "validate.error");

    private String code;

    private String message;

    DefaultErrorCode(String code, String message){
        this.code = code;
        this.message= message;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
}
