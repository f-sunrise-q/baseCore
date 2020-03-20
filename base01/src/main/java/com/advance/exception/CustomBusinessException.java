package com.advance.exception;

import com.advance.reponse.IErrorCode;

/**
 * 自定义业务异常，一般用于将一些系统错误包装为业务异常往上层抛出
 *
 * Created by qin on 20/3/19.
 */
public class CustomBusinessException extends RuntimeException {

    private String code;
    private Object[] params;

    public CustomBusinessException(String code, Object[] params) {
        this.code = code;
        this.params = params;
    }

    public CustomBusinessException(String message, String code, Object[] params) {
        super(message);
        this.code = code;
        this.params = params;
    }

    public CustomBusinessException(String message, Throwable cause, String code, Object[] params) {
        super(message, cause);
        this.code = code;
        this.params = params;
    }

    public CustomBusinessException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public CustomBusinessException(IErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.code = errorCode.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
